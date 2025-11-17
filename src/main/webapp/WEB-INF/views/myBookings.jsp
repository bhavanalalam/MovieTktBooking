<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <title>My Bookings</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2>My Bookings</h2>
    <c:if test="${empty bookings}">
        <p>No bookings found.</p>
    </c:if>
    <c:if test="${not empty bookings}">
        <table class="table table-striped mt-3">
            <thead>
                <tr><th>Booking ID</th><th>Movie</th><th>Show Time</th><th>Amount</th><th>Status</th><th>Action</th></tr>
            </thead>
            <tbody>
                <c:forEach var="booking" items="${bookings}">
                    <tr>
                        <td>${booking.id}</td>
                        <td>${booking.show.movie.title}</td>
                        <td>${booking.show.showTime}</td>
                        <td>Rs.${booking.amount}</td>
                        <td>${booking.status}</td>
                        <td>
                            <c:if test="${booking.status eq 'CONFIRMED'}">
                                <form action="${pageContext.request.contextPath}/booking/cancel" method="post">
                                    <input type="hidden" name="bookingId" value="${booking.id}">
                                    <button class="btn btn-danger btn-sm">Cancel</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
</body>
</html>
