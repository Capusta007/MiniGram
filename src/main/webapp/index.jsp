<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8">
<title>MiniGram — главная</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container-fluid">
			<a class="navbar-brand" href="homepage">MiniGram</a>
			<div class="d-flex">
				<a href="login" class="btn btn-outline-light me-2">Вход</a> <a
					href="registration" class="btn btn-light">Регистрация</a>
			</div>
		</div>
	</nav>

	<div class="container mt-5 text-center">
		<h1 class="display-4">Добро пожаловать в MiniGram!</h1>
		<p class="lead">Мини-социальная сеть, где вы можете делиться
			моментами, лайкать и комментировать посты друзей.</p>
		<div class="mt-4">
			<a href="registration" class="btn btn-primary btn-lg me-3">Зарегистрироваться</a>
			<a href="login" class="btn btn-outline-secondary btn-lg">Войти</a>
		</div>
	</div>

	<footer class="footer mt-auto py-3 bg-light fixed-bottom">
		<div class="container text-center">
			<span class="text-muted">&copy; 2025 MiniGram</span>
		</div>
	</footer>

</body>

<!-- Подключение Bootstrap JS -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
</html>
