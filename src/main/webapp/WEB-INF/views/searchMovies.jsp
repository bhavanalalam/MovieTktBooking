<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Search Movies</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2>Find Movies</h2>
    <form action="${pageContext.request.contextPath}/movies/search" method="get" class="row g-3">
        <div class="col-md-3">
            <input name="city" class="form-control" placeholder="City" required>
        </div>
        <div class="col-md-3">
            <input name="date" type="date" class="form-control" required>
        </div>
        <div class="col-md-3">
            <input name="language" class="form-control" placeholder="Language">
        </div>
        <div class="col-md-3">
            <button class="btn btn-primary w-100">Search</button>
        </div>
    </form>

    <c:if test="${not empty movies}">
        <div class="mt-4">
            <h4>Available Movies</h4>
            <table class="table table-striped">
                <thead>
                    <tr><th>Title</th><th>Language</th><th>Duration</th><th></th></tr>
                </thead>
                <tbody>
                    <c:forEach var="movie" items="${movies}">
                        <tr>
                            <td>${movie.title}</td>
                            <td>${movie.language}</td>
                            <td>${movie.duration} mins</td>
                            <td><a href="${pageContext.request.contextPath}/shows?movieId=${movie.id}" 
                                   class="btn btn-outline-primary btn-sm">View Shows</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>
</body>
</html>
