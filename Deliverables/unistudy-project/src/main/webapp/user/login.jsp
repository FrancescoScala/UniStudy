<%--
  Created by IntelliJ IDEA.
  User: francesco
  Date: 04/02/23
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>UniStudy</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato:300,400,700&amp;display=swap">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/fonts/font-awesome.min.css">
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/assets/css/-Login-form-Page-BS4--Login-form-Page-BS4.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/Banner-Heading-Image-images.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/Custom-File-Upload.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/Cute-Select.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/css/pikaday.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/Multiple-Input-Select-Pills.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/Search-Input-Responsive-with-Icon.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/Sidebar-Menu-sidebar.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/Sidebar-Menu.css">
</head>

<body>
<div class="container-fluid">
    <div class="row mh-100vh">
        <div class="col-10 col-sm-8 col-md-6 col-lg-6 offset-1 offset-sm-2 offset-md-3 offset-lg-0 align-self-center d-lg-flex align-items-lg-center align-self-lg-stretch bg-white p-5 rounded rounded-lg-0 my-5 my-lg-0"
             id="login-block">
            <div class="m-auto w-lg-75 w-xl-50">
                <h2 class="text-info fw-light mb-5"><i class="fa fa-book"
                                                       style="padding-right: 0px;margin-right: 10px;"></i>Unistudy</h2>
                <%
                    if (request.getParameter("msg") != null &&
                            request.getParameter("msg").equals("signupOK")) {
                %>
                <h3><span id="mex">Registrazione andata a buon fine!</span></h3>
                <%
                    }
                    if (request.getParameter("msg") != null &&
                            request.getParameter("msg").equals("loginError")) {
                %>
                <h3><span id="mex">Email e/o password errata, si prega di riprovare</span></h3>
                <%
                    }
                %>
                <form method="post" action="<%=request.getContextPath()%>/user/MemberControl">
                    <input type="hidden" name="action" value="login">
                    <div class="form-group mb-3"><label class="form-label text-secondary">Email</label><input
                            class="form-control" id="email" type="text" required="" name="email"
                            pattern="[a-zA-z0-9._%+-]+@[a-zA-z0-9.-]+\.[a-z]{2,15}$" inputmode="email"
                            oninvalid="this.setCustomValidity('Inserisci un indirizzo email valido. Es: mail@mail.com')"
                            onchange="this.setCustomValidity('')"></div>
                    <div class="form-group mb-3"><label class="form-label text-secondary">Password</label><input
                            class="form-control" id="password" type="password" name="password" required=""
                            pattern="^(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\w\d\s:])([^\s]){8,12}$"
                            oninvalid="this.setCustomValidity('Inserisci una password valida. La psw deve essere tra gli 8 e i 12 ' +
                             'caratteri e deve contenere almeno una lettera MAIUSCOLA, una minuscola, un numero e un carattere speciale.')"
                            onchange="this.setCustomValidity('')"></div>
                    <button class="btn btn-info mt-2" type="submit">Log In</button>
                </form>
                <p class="mt-3 mb-0"><a class="text-info small" href="<%=request.getContextPath()%>/user/signup.jsp">Oppure
                    clicca qui per registrarti</a>
                </p>
            </div>
        </div>
        <div class="col-lg-6 d-flex align-items-end" id="bg-block"
             style="background: url(&quot;assets/img/tech/image4.jpg&quot;) center / cover, url(&quot;../assets/img/tech/image4.jpg&quot;) center / cover no-repeat;">
            <p class="ms-auto small text-dark mb-2"></p>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath()%>/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/Custom-File-Upload.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/pikaday.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/Sidebar-Menu.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/theme.js"></script>
</body>

</html>