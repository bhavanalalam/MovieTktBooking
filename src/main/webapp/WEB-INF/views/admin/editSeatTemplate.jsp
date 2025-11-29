<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="container mt-4">

    <h2>Configure Seats for Template: ${template.name}</h2>

    <!-- UPDATE rows/cols -->
    <form method="post" action="${pageContext.request.contextPath}/admin/templates/edit">
        <input type="hidden" name="id" value="${template.id}">
        <input type="hidden" name="screen.id" value="${screen.id}">

        <div class="row mt-3">
            <div class="col-md-4">
                <label>Rows</label>
                <input class="form-control" type="number" name="rows" value="${template.rows}">
            </div>

            <div class="col-md-4">
                <label>Columns</label>
                <input class="form-control" type="number" name="cols" value="${template.cols}">
            </div>

            <div class="col-md-4">
                <label>&nbsp;</label><br>
                <button class="btn btn-primary">Update Layout</button>
            </div>
        </div>
    </form>

    <hr>

    <h4>Seat Layout</h4>

    <form method="post" action="${pageContext.request.contextPath}/admin/seats/save">
        <input type="hidden" name="templateId" value="${template.id}"/>

        <div class="seat-grid"
             style="
                display: grid;
                grid-template-columns: repeat(${template.cols}, 55px);
                gap: 8px;
                margin-top:20px;
             ">

            <c:forEach var="seat" items="${template.seats}">
                <div style="text-align:center">
                    <input type="hidden" name="ids" value="${seat.id}">
                    <input type="text" class="form-control form-control-sm"
                           name="codes" value="${seat.seatCode}" placeholder="A1">
                    <select class="form-select form-select-sm mt-1"
                            name="types">
                        <option ${seat.seatType=='REGULAR'?'selected':''}>REGULAR</option>
                        <option ${seat.seatType=='VIP'?'selected':''}>VIP</option>
                        <option ${seat.seatType=='DISABLED'?'selected':''}>DISABLED</option>
                    </select>
                </div>
            </c:forEach>

        </div>

        <button class="btn btn-success mt-3">Save All Seats</button>
    </form>
</div>
