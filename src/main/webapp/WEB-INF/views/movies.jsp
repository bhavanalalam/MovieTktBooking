<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container mt-4">
    <h2 class="mb-4 text-center"> Now Showing</h2>

    <!-- Search Form -->
    <form method="get" action="${pageContext.request.contextPath}/movies" class="d-flex justify-content-center mb-4">
        <input type="text" name="search" value="${search}" placeholder="Search by title or genre"
               class="form-control" style="width: 300px; margin-right: 10px;">
        <button type="submit" class="btn btn-primary">Search</button>
    </form>

    <!-- Movie Cards Grid -->
    <div class="row">
        <c:forEach var="m" items="${movies}">
            <div class="col-md-3 col-sm-6 mb-4">
                <div class="card shadow-sm" style="border-radius: 10px;">
                    <c:if test="${not empty m.posterUrl}">
                        <img src="${m.posterUrl}" class="card-img-top" alt="${m.title}" style="height: 320px; object-fit: cover;">
                    </c:if>
                    <div class="card-body">
                        <h5 class="card-title">${m.title}</h5>
                        <p class="card-text">
                            <small><strong>Language:</strong> ${m.language}</small><br/>
                            <small><strong>Genre:</strong> ${m.genre}</small><br/>
                            <small><strong>Duration:</strong> ${m.duration} min</small>
                        </p>
                        <a href="${pageContext.request.contextPath}/user/movies/${m.id}/shows" class="btn btn-outline-primary btn-sm">
                            View Showtimes
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${empty movies}">
        <div class="alert alert-info text-center">No movies found matching your search.</div>
    </c:if>
</div>
