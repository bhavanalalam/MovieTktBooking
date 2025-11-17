<div class="container mt-4 text-center">
    <h2> Booking Confirmed!</h2>
    <p>Your booking ID: <strong>${booking.id}</strong></p>
    <p>Show: ${booking.show.movie.title} at ${booking.show.showTime}</p>
    <p>Seats:
        <c:forEach var="seat" items="${booking.seats}">
            <span class="badge bg-primary">${seat.seatCode}</span>
        </c:forEach>
    </p>
    <p>Total Paid: Rs. ${booking.amount}</p>
    <a href="${pageContext.request.contextPath}/booking/history" class="btn btn-secondary mt-3">View Booking History</a>
</div>
