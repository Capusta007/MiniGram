<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html lang="ru">
<head>
<meta charset="UTF-8">
<title>Главная страница — MiniGram</title>
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
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><a class="nav-link active" href="home">Главная</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="profile">Профиль</a>
					</li>
					<!-- Можно добавить еще ссылки позже -->
				</ul>
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link text-danger"
						href="logout">Выход</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Лента постов -->
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-8">
				<%-- Тут будет вывод постов. Пока статично --%>
				<div class="card mb-3">
					<div class="card-body">
						<h5 class="card-title">Пользователь: Иван Иванов</h5>
						<p class="card-text">Это мой первый пост в MiniGram! 👋</p>
						<p class="card-text">
							<small class="text-muted">10 минут назад</small>
						</p>
					</div>
				</div>

				<div class="card mb-3">
					<div class="card-body">
						<h5 class="card-title">Пользователь: Анна Смирнова</h5>
						<p class="card-text">Сегодня отличная погода! ☀️</p>
						<p class="card-text">
							<small class="text-muted">1 час назад</small>
						</p>
					</div>
				</div>

				<%-- Тут потом можно будет подключить цикл по настоящим постам --%>
			</div>
		</div>
	</div>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

</body>
</html>
