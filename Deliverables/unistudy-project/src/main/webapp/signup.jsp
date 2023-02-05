<%--
  Created by IntelliJ IDEA.
  User: nickm
  Date: 05/02/2023
  Time: 18:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script
            src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
    </script>

    <title>Signup</title>

    <script type="text/javascript">
        $(document).ready(function () {

            $("#signupButton").click(chiamaControl);

            function chiamaControl() {

                var jqxhr = $.post("UserControl",
                    {
                        "action": $("#action").val(),
                        "email": $("#email").val(),
                        "password": $("#password").val(),
                        "name": $("#name").val(),
                        "surname": $("#surname").val()
                    },
                    function (data) {
                        var success = "\"OK\"";
                        var result = (JSON.stringify(data.result)).localeCompare(success);

                        if (result == 0) {
                            window.location.href = "<%=request.getContextPath()+"/login.jsp?msg=signupOK"%>";
                        } else {
                            $("#mex").html(data.result);
                        }
                    });
            }

        });
    </script>

</head>
<body>
<div>
    <h3><span id="mex"></span></h3>
</div>
<form method="post" onSubmit="return false;">
    <input type="hidden" id="action" name="action" value="signup">
    <input type="text" id="email" name="email" placeholder="Email">
    <input type="text" id="password" name="password" placeholder="Password">
    <input type="text" id="name" name="name" placeholder="Nome">
    <input type="text" id="surname" name="surname" placeholder="Cognome">
    <button id="signupButton" type="submit">ISCRIVIMI</button>
</form>
</body>
</html>

