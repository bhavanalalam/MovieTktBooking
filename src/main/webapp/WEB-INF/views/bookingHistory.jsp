<div class="container mt-4">
    <h2>My Bookings</h2>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Movie</th>
            <th>Show Time</th>
            <th>Status</th>
            <th>Amount</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="b" items="${bookings}">
            <tr>
                <td>${b.id}</td>
                <td>${b.show.movie.title}</td>
                <td>${b.show.showTime}</td>
                <td>${b.status}</td>
                <td>Rs. ${b.amount}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
