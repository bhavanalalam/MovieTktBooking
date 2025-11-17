<div class="container mt-4">
    <div class="row">
        <div class="col-md-4">
            <c:if test="${not empty movie.posterUrl}">
                <img src="${movie.posterUrl}" class="img-fluid rounded shadow-sm" alt="${movie.title}">
            </c:if>
        </div>
        <div class="col-md-8">
            <h2>${movie.title}</h2>
            <p><strong>Genre:</strong> ${movie.genre}</p>
            <p><strong>Language:</strong> ${movie.language}</p>
            <p><strong>Duration:</strong> ${movie.duration} min</p>
            <p>${movie.description}</p>
        </div>
    </div>

    <hr/>

    <h4 class="mt-4">Available Shows</h4>
    <c:if test="${not empty shows}">
        <table class="table table-bordered">
            <thead class="table-light">
            <tr>
                <th>Theater</th>
                <th>Screen</th>
                <th>Show Time</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="s" items="${shows}">
                <tr>
                    <td>${s.screen.theater.name}</td>
                    <td>${s.screen.name}</td>
                    <td>${s.showTime}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/booking/select/${s.id}" class="btn btn-success btn-sm">
                            Book Now
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty shows}">
        <div class="alert alert-warning">No upcoming shows available for this movie.</div>
    </c:if>
</div>
