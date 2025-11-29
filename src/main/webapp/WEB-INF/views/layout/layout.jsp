<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${pageTitle != null ? pageTitle : 'Movie Ticket Booking'}"/></title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

    <style>
        body { background-color: #f8fafc; }

        /* Space so navbar never overlaps content */
        .container-main {
            margin-top: 120px !important;
            min-height: 80vh;
        }

        .navbar {
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 2000;
        }

        .card { transition: 0.3s; }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 20px rgba(0,0,0,0.15);
        }

        footer {
            background: #1e293b;
            color: white;
        }
    </style>
</head>

<body>

<!-- HEADER -->
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<!-- NAVBAR -->
<jsp:include page="/WEB-INF/views/layout/navbar.jsp" />

<!-- MAIN CONTENT -->
<div class="container container-main">
    <jsp:include page="${contentPage}" />
</div>

<!-- Seat Click Script -->
<script>
document.addEventListener("DOMContentLoaded", function () {
    const selectedSeats = new Set();
    const seats = document.querySelectorAll('.seat');

    seats.forEach(seat => {
        seat.addEventListener('click', () => {
            seat.classList.toggle('selected');
            const id = seat.dataset.id;

            if (selectedSeats.has(id)) selectedSeats.delete(id);
            else selectedSeats.add(id);

            const input = document.getElementById('seatIds');
            if (input) input.value = Array.from(selectedSeats);
        });
    });
});
</script>

<!-- FOOTER -->
<jsp:include page="/WEB-INF/views/layout/footer.jsp" />

</body>
</html>
