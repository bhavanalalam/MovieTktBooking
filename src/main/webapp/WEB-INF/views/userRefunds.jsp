<div class="container mt-4">
    <h2> My Refunds</h2>
    <table class="table table-bordered mt-3">
        <thead class="table-light">
            <tr>
                <th>Refund ID</th>
                <th>Booking ID</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Processed At</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="r" items="${refunds}">
                <tr>
                    <td>${r.id}</td>
                    <td>${r.booking.id}</td>
                    <td>Rs. ${r.amount}</td>
                    <td>
                        <c:choose>
                            <c:when test="${r.status eq 'SUCCESS'}"><span class="text-success">SUCCESS</span></c:when>
                            <c:when test="${r.status eq 'INITIATED'}"><span class="text-warning">IN PROGRESS</span></c:when>
                            <c:otherwise><span class="text-danger">FAILED</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>${r.processedAt}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty refunds}">
        <div class="alert alert-info mt-3">No refunds found.</div>
    </c:if>
</div>
