<div class="container mt-4">
    <h2>Confirm Booking</h2>
    <p>Movie: <strong>${booking.show.movie.title}</strong></p>
    <p>Show Time: <strong>${booking.show.showTime}</strong></p>
    <p>Seats:
        <c:forEach var="seat" items="${booking.seats}">
            <span class="badge bg-primary">${seat.seatCode}</span>
        </c:forEach>
    </p>
    <p>Total Amount: Rs. ${booking.amount}</p>

    <form method="post" action="${pageContext.request.contextPath}/booking/confirm">
        <input type="hidden" name="bookingId" value="${booking.id}"/>
        <button class="btn btn-success">Confirm & Pay</button>
    </form>
</div>
