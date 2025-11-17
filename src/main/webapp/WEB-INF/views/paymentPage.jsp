<div class="container mt-4 text-center">
    <h2>Make Payment</h2>
    <p>Booking ID: <strong>${booking.id}</strong></p>
    <p>Amount to Pay: Rs. ${booking.amount}</p>
    <p>Gateway: ${payment.gateway}</p>

    <button id="payButton" class="btn btn-success">Pay Now</button>
</div>

<script>
    document.getElementById('payButton').addEventListener('click', function() {
        alert('Redirecting to payment gateway...');
        setTimeout(() => {
            // Simulate success webhook for demo
            fetch('${pageContext.request.contextPath}/payment/webhook', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({status: 'payment_success', txn_id: '${payment.txnId}'})
            }).then(() => {
                window.location.href = '${pageContext.request.contextPath}/booking/confirm?bookingId=${booking.id}';
            });
        }, 1500);
    });
</script>
