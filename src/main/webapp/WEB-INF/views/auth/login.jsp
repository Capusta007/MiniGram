<%@page import="util.RequestAttributeUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8">
<title>Вход — MiniGram</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
</head>
<body>

	<!-- Навбар -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
		<div class="container-fluid">
			<a class="navbar-brand" href="homepage">MiniGram</a>
		</div>
	</nav>

	<!-- Контент страницы входа -->
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card">
					<div class="card-header">
						<h4 class="card-title">Вход в аккаунт</h4>
					</div>
					<div class="card-body">
						<form action="login" method="POST">
							<div class="mb-3">
								<label for="email" class="form-label">Email</label> <input
									type="text" class="form-control" id="email" name="email"
									required>
							</div>
							<div class="mb-3">
								<label for="password" class="form-label">Пароль</label> <input
									type="password" class="form-control" id="password"
									name="password" required>
							</div>
							<button type="submit" class="btn btn-primary w-100">Войти</button>
						</form>
						<div class="mt-3 text-center">
							<p>
								Нет аккаунта? <a href="registration">Создать аккаунт</a>
							</p>
						</div>
						<%String error = RequestAttributeUtil.getErrorMessage(request);%>
						<%if(error != null){ %>
							<div class = "alert alert-danger" ><%=error%></div>
						<%} %>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Подключение Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

</body>
</html>
