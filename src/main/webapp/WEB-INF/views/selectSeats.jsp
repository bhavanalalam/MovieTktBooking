<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="container mt-4">

    <h2 class="text-center mb-4">Select Seats for ${show.movie.title}</h2>

    <form id="bookingForm" method="post" action="${pageContext.request.contextPath}/booking/hold">

        <input type="hidden" name="showId" value="${show.id}" />
        <input type="hidden" name="seatIds" id="seatIds"/>

        <!-- CENTER THE GRID + LIMIT MAX WIDTH -->
        <div class="seat-grid mx-auto"
             style="
                display: grid;
                grid-template-columns: repeat(${show.seatTemplate.cols}, 45px);
                gap: 8px;
                max-width: 95%;
                justify-content: center;
                margin-bottom: 30px;
             ">

            <c:forEach var="seat" items="${templateSeats}">
                <div class="seat ${seat.seatType.toLowerCase()}"
                     data-id="${seat.id}">
                    ${seat.seatCode}
                </div>
            </c:forEach>

        </div>

        <div class="text-center">
            <button class="btn btn-primary px-4">Proceed to Confirm</button>
        </div>

    </form>
</div>

<style>
    .seat {
        width: 45px;
        height: 45px;
        border-radius: 6px;
        text-align: center;
        line-height: 45px;
        cursor: pointer;
        user-select: none;
        font-weight: bold;
        background: #6c757d;
        color: white;
        transition: 0.2s;
    }

    .seat.selected {
        background: #28a745 !important;
        border: 2px solid white;
        color: white !important;
    }
    
</style>

<script>
document.addEventListener("DOMContentLoaded", function () {

    const selected = new Set();
    const seats = document.querySelectorAll(".seat");
    const input = document.getElementById("seatIds");

    seats.forEach(seat => {
        seat.addEventListener("click", () => {

            const id = seat.dataset.id;
            seat.classList.toggle("selected");

            if (selected.has(id)) selected.delete(id);
            else selected.add(id);

            input.value = Array.from(selected).join(",");
        });
    });

});
</script>
