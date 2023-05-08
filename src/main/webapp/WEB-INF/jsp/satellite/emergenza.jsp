<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="it" class="h-100">
<head>

<!-- Common imports in pages -->
<jsp:include page="../header.jsp" />

<title>Prodecura di Emergenza</title>

</head>
<body class="d-flex flex-column h-100">

	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp"></jsp:include>


	<!-- Begin page content -->
	<main class="flex-shrink-0">
		<div class="container">

			<div
				class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }"
				role="alert">
				${errorMessage}
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>


			<div class='card'>
				<div class='card-header'>
					<h5>Sta per essere applicata la procedura di emergenza. Si è
						sicuri di voler procedere?</h5>
				</div>


				<div class='card-body'>
					<dl class="row">
						<dt class="col-sm-3 text-right">Numero Voci totali presenti
							nel sistema:</dt>
						<dd class="col-sm-9">${size_list_all}</dd>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Numero Voci che verranno
							modificate in seguito alla procedura:</dt>
						<dd class="col-sm-9">${size_list_emergenza}</dd>
					</dl>




				</div>
				<!-- end card body -->

				<div class='card-footer'>




					<form method="post"
						action="${pageContext.request.contextPath}/satellite/emergenza"
						novalidate="novalidate">
						<input type="hidden" name="sizeEmergenza"
							value="${size_list_emergenza}">

						<button type="submit" name="submit" id="submit"
							class="btn btn-danger">Conferma</button>
						<a href="${pageContext.request.contextPath}/home"
							class='btn btn-outline-secondary' style='width: 80px'> <i
							class='fa fa-chevron-left'></i> Back
						</a>
					</form>

				</div>
				<!-- end card -->
			</div>


			<!-- end container -->
		</div>

	</main>

	<!-- Footer -->
	<jsp:include page="../footer.jsp" />
</body>
</html>