<div class="container mt-4">
    <h2>${pageTitle}</h2>
    <form id="bookingForm" method="post" action="${pageContext.request.contextPath}/booking/hold">
        <input type="hidden" name="showId" value="${show.id}" />
        <div class="seat-grid" style="display: grid; grid-template-columns: repeat(${show.seatTemplate.cols}, 40px); gap: 6px;">
            <c:forEach var="seat" items="${templateSeats}">
                <div class="seat ${seat.seatType.toLowerCase()}" data-id="${seat.id}" data-price="${seat.price}">
                    ${seat.seatCode}
                </div>
            </c:forEach>
        </div>
        <input type="hidden" name="seatIds" id="seatIds"/>
        <button class="btn btn-primary mt-3">Proceed to Confirm</button>
    </form>
</div>

<script>
    const selectedSeats = new Set();
    document.querySelectorAll('.seat').forEach(seat => {
        seat.addEventListener('click', () => {
            seat.classList.toggle('selected');
            const id = seat.dataset.id;
            if (selectedSeats.has(id)) selectedSeats.delete(id);
            else selectedSeats.add(id);
            document.getElementById('seatIds').value = Array.from(selectedSeats);
        });
    });
</script>

<style>
    .seat { width: 40px; height: 40px; border-radius: 4px; text-align: center; line-height: 40px; cursor: pointer; }
    .seat.selected { background-color: #28a745; color: #fff; }
    .seat.regular { background-color: #6c757d; color: #fff; }
    .seat.vip { background-color: #ffc107; color: #000; }
</style>
