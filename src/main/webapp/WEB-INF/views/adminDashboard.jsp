<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<div class="container mt-4">
    <h2 class="mb-4 text-center">
        🎭 Theater Admin Dashboard
    </h2>

    <div class="alert alert-info text-center">
        Welcome, <strong>${sessionScope.user.fullName}</strong>!  
        Manage your theater’s movies, screens, and show schedules below.
    </div>

    <!-- Tabs for navigation -->
    <ul class="nav nav-tabs" id="adminTabs" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" id="movies-tab" data-bs-toggle="tab" data-bs-target="#movies" type="button" role="tab">Movies</button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="screens-tab" data-bs-toggle="tab" data-bs-target="#screens" type="button" role="tab">Screens</button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="shows-tab" data-bs-toggle="tab" data-bs-target="#shows" type="button" role="tab">Shows</button>
        </li>
    </ul>

    <div class="tab-content mt-3" id="adminTabsContent">

        <!-- ======================= MOVIES TAB ======================= -->
        <div class="tab-pane fade show active" id="movies" role="tabpanel" aria-labelledby="movies-tab">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <h5>🎞 Movies Playing in Your Theater</h5>
                <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#addMovieModal">
                    <i class="fa fa-plus"></i> Add Movie
                </button>
            </div>

            <table class="table table-striped">
                <thead>
                    <tr><th>Title</th><th>Language</th><th>Duration</th><th>Genre</th><th></th></tr>
                </thead>
                <tbody>
                    <c:forEach var="movie" items="${movies}">
                        <tr>
                            <td>${movie.title}</td>
                            <td>${movie.language}</td>
                            <td>${movie.duration} mins</td>
                            <td>${movie.genre}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/deleteMovie" method="post" class="d-inline">
                                    <input type="hidden" name="movieId" value="${movie.id}" />
                                    <button class="btn btn-sm btn-danger"><i class="fa fa-trash"></i></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- ======================= SCREENS TAB ======================= -->
        <div class="tab-pane fade" id="screens" role="tabpanel" aria-labelledby="screens-tab">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <h5>Screens</h5>
                <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#addScreenModal">
                    <i class="fa fa-plus"></i> Add Screen
                </button>
            </div>

            <table class="table table-striped">
                <thead><tr><th>Name</th><th>Description</th><th>Actions</th></tr></thead>
                <tbody>
                    <c:forEach var="screen" items="${screens}">
                        <tr>
                            <td>${screen.name}</td>
                            <td>${screen.description}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/deleteScreen" method="post" class="d-inline">
                                    <input type="hidden" name="screenId" value="${screen.id}" />
                                    <button class="btn btn-sm btn-danger"><i class="fa fa-trash"></i></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- ======================= SHOWS TAB ======================= -->
        <div class="tab-pane fade" id="shows" role="tabpanel" aria-labelledby="shows-tab">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <h5>🕒 Show Timings</h5>
                <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#addShowModal">
                    <i class="fa fa-plus"></i> Schedule Show
                </button>
            </div>

            <table class="table table-striped">
                <thead><tr><th>Movie</th><th>Screen</th><th>Show Time</th><th>Total Seats</th><th></th></tr></thead>
                <tbody>
                    <c:forEach var="show" items="${shows}">
                        <tr>
                            <td>${show.movie.title}</td>
                            <td>${show.screen.name}</td>
                            <td>${show.showTime}</td>
                            <td>${show.totalSeats}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/deleteShow" method="post" class="d-inline">
                                    <input type="hidden" name="showId" value="${show.id}" />
                                    <button class="btn btn-sm btn-danger"><i class="fa fa-trash"></i></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- ======================= ADD MOVIE MODAL ======================= -->
<div class="modal fade" id="addMovieModal" tabindex="-1">
  <div class="modal-dialog">
    <form action="${pageContext.request.contextPath}/admin/addMovie" method="post" class="modal-content">
      <div class="modal-header bg-dark text-white">
        <h5 class="modal-title">Add Movie</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <input type="text" name="title" class="form-control mb-2" placeholder="Title" required />
        <input type="text" name="language" class="form-control mb-2" placeholder="Language" required />
        <input type="number" name="duration" class="form-control mb-2" placeholder="Duration (minutes)" required />
        <input type="text" name="genre" class="form-control mb-2" placeholder="Genre" />
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-success">Add Movie</button>
      </div>
    </form>
  </div>
</div>

<!-- ======================= ADD SCREEN MODAL ======================= -->
<div class="modal fade" id="addScreenModal" tabindex="-1">
  <div class="modal-dialog">
    <form action="${pageContext.request.contextPath}/admin/addScreen" method="post" class="modal-content">
      <div class="modal-header bg-dark text-white">
        <h5 class="modal-title">Add Screen</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <input type="text" name="name" class="form-control mb-2" placeholder="Screen Name" required />
        <textarea name="description" class="form-control mb-2" placeholder="Description"></textarea>
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-success">Add Screen</button>
      </div>
    </form>
  </div>
</div>

<!-- ======================= ADD SHOW MODAL ======================= -->
<div class="modal fade" id="addShowModal" tabindex="-1">
  <div class="modal-dialog">
    <form action="${pageContext.request.contextPath}/admin/addShow" method="post" class="modal-content">
      <div class="modal-header bg-dark text-white">
        <h5 class="modal-title">Schedule New Show</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <select name="movieId" class="form-select mb-2" required>
          <option value="">Select Movie</option>
          <c:forEach var="movie" items="${movies}">
            <option value="${movie.id}">${movie.title}</option>
          </c:forEach>
        </select>
        <select name="screenId" class="form-select mb-2" required>
          <option value="">Select Screen</option>
          <c:forEach var="screen" items="${screens}">
            <option value="${screen.id}">${screen.name}</option>
          </c:forEach>
        </select>
        <input type="datetime-local" name="showTime" class="form-control mb-2" required />
        <input type="number" name="totalSeats" class="form-control mb-2" placeholder="Total Seats" required />
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-success">Add Show</button>
      </div>
    </form>
  </div>
</div>
