<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Refund Status</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5 col-md-6">
    <div class="card p-4 text-center shadow-sm">
        <h3>Refund Status</h3>
        <p><strong>Booking ID:</strong> ${refund.booking.id}</p>
        <p><strong>Refund ID:</strong> ${refund.txnId}</p>
        <p><strong>Amount:</strong> Rs. ${refund.amount}</p>
        <p><strong>Status:</strong> 
           <span class="badge ${refund.status eq 'SUCCESS' ? 'bg-success' : 'bg-danger'}">
               ${refund.status}
           </span>
        </p>
        <a href="${pageContext.request.contextPath}/user/bookings" class="btn btn-primary mt-3">Back to My Bookings</a>
    </div>
</div>
</body>
</html>
