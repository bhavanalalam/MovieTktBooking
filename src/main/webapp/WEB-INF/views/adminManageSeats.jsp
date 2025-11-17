<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<div class="container mt-4">
    <h2 class="mb-4 text-center">Manage Seat Templates</h2>

    <div class="alert alert-secondary text-center">
        Design seating layouts for your theater screens — define rows, columns, seat types, and prices.
    </div>

    <!-- Seat Template Selection -->
    <form action="${pageContext.request.contextPath}/admin/loadSeatTemplate" method="get" class="row g-3 align-items-end mb-4">
        <div class="col-md-4">
            <label class="form-label">Select Screen</label>
            <select name="screenId" class="form-select" required>
                <option value="">-- Choose Screen --</option>
                <c:forEach var="screen" items="${screens}">
                    <option value="${screen.id}" ${screen.id == selectedScreenId ? 'selected' : ''}>
                        ${screen.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-4">
            <label class="form-label">Seat Template</label>
            <select name="templateId" class="form-select">
                <option value="">-- Select Template --</option>
                <c:forEach var="template" items="${seatTemplates}">
                    <option value="${template.id}" ${template.id == selectedTemplateId ? 'selected' : ''}>
                        ${template.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-4">
            <button class="btn btn-primary w-100" type="submit">
                <i class="fa fa-edit"></i> Load Template
            </button>
        </div>
    </form>

    <!-- Create New Template -->
    <form action="${pageContext.request.contextPath}/admin/createSeatTemplate" method="post" class="card p-3 mb-4 shadow-sm">
        <h5>➕ Create New Template</h5>
        <div class="row g-3">
            <div class="col-md-4">
                <input name="name" class="form-control" placeholder="Template Name" required>
            </div>
            <div class="col-md-3">
                <input name="rows" type="number" class="form-control" placeholder="Rows" required>
            </div>
            <div class="col-md-3">
                <input name="cols" type="number" class="form-control" placeholder="Columns" required>
            </div>
            <div class="col-md-2">
                <button class="btn btn-success w-100" type="submit">
                    <i class="fa fa-plus"></i> Create
                </button>
            </div>
        </div>
    </form>

    <!-- Seat Grid Designer -->
    <c:if test="${not empty seatGrid}">
        <div class="card shadow-sm p-3 mb-4">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h5>🪑 Edit Template: ${currentTemplate.name}</h5>
                <button id="saveLayoutBtn" class="btn btn-primary btn-sm">
                    <i class="fa fa-save"></i> Save Layout
                </button>
            </div>

            <div id="seat-grid" class="d-flex flex-column align-items-center mb-3">
                <c:forEach var="row" items="${seatGrid}">
                    <div class="d-flex justify-content-center mb-2">
                        <c:forEach var="seat" items="${row}">
                            <button type="button"
                                    class="seat-btn btn btn-sm me-1 
                                           ${seat.seatType eq 'PREMIUM' ? 'btn-warning' 
                                           : seat.seatType eq 'REGULAR' ? 'btn-secondary' 
                                           : seat.seatType eq 'HANDICAP' ? 'btn-info' : 'btn-outline-secondary'}"
                                    data-id="${seat.id}"
                                    data-type="${seat.seatType}"
                                    data-price="${seat.price}"
                                    title="${seat.seatCode} - ₹${seat.price}">
                                ${seat.seatCode}
                            </button>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>

            <div class="alert alert-light border">
                <strong>Seat Legend:</strong>
                <span class="badge bg-warning text-dark">Premium</span>
                <span class="badge bg-secondary">Regular</span>
                <span class="badge bg-info text-dark">Handicap</span>
                <span class="badge bg-outline-secondary border text-muted">Empty</span>
            </div>
        </div>
    </c:if>
</div>

<!-- Modal for Editing Seat Details -->
<div class="modal fade" id="editSeatModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-dark text-white">
                <h5 class="modal-title">Edit Seat Details</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form id="editSeatForm">
                    <input type="hidden" id="seatId">
                    <div class="mb-3">
                        <label class="form-label">Seat Type</label>
                        <select id="seatType" class="form-select">
                            <option value="REGULAR">Regular</option>
                            <option value="PREMIUM">Premium</option>
                            <option value="HANDICAP">Handicap</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Price (₹)</label>
                        <input type="number" id="seatPrice" class="form-control" step="1" min="0">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button class="btn btn-success" id="saveSeatChangesBtn">Save</button>
            </div>
        </div>
    </div>
</div>

<script>
    // JS logic for seat editing
    const editModal = new bootstrap.Modal(document.getElementById('editSeatModal'));
    let selectedSeatBtn = null;

    document.querySelectorAll('.seat-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            selectedSeatBtn = btn;
            document.getElementById('seatId').value = btn.dataset.id;
            document.getElementById('seatType').value = btn.dataset.type;
            document.getElementById('seatPrice').value = btn.dataset.price;
            editModal.show();
        });
    });

    document.getElementById('saveSeatChangesBtn').addEventListener('click', () => {
        const type = document.getElementById('seatType').value;
        const price = document.getElementById('seatPrice').value;

        selectedSeatBtn.dataset.type = type;
        selectedSeatBtn.dataset.price = price;
        selectedSeatBtn.title = `${selectedSeatBtn.innerText} - ₹${price}`;

        selectedSeatBtn.className = `seat-btn btn btn-sm me-1 ${
            type === 'PREMIUM' ? 'btn-warning' :
            type === 'HANDICAP' ? 'btn-info' : 'btn-secondary'
        }`;

        editModal.hide();
    });

    document.getElementById('saveLayoutBtn')?.addEventListener('click', async () => {
        const seats = Array.from(document.querySelectorAll('.seat-btn')).map(btn => ({
            id: btn.dataset.id,
            type: btn.dataset.type,
            price: btn.dataset.price
        }));

        await fetch('${pageContext.request.contextPath}/admin/saveSeatLayout', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(seats)
        });

        alert('Seat layout saved successfully!');
    });
</script>
