<%--
  Created by IntelliJ IDEA.
  User: nickm
  Date: 06/02/2023
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script
            src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
    </script>

    <title>Aggiungi corso</title>

    <script type="text/javascript">
        $(document).ready(function () {

            $("#createCourseButton").click(chiamaControl);

            function chiamaControl() {

                var jqxhr = $.post("CourseControl",
                    {
                        "action": $("#action").val(),
                        "title": $("#title").val(),
                        "professors": $("#professors").val(),
                        "schedule": $("#schedule").val()
                    },
                    function (data) {
                        var success = "\"OK\"";
                        var result = (JSON.stringify(data.result)).localeCompare(success);

                        if (result == 0) {
                            $("#mex").html("Aggiunta effettuata");
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
    <input type="hidden" id="action" name="action" value="create">
    <input type="text" id="title" name="title" placeholder="Titolo">
    <input type="text" id="professors" name="professors" placeholder="NomeProf1 CognomeProf1, NomeProf2 CognomeProf2, ...">
    <input type="text" id="schedule" name="schedule" placeholder="Orario">
    <button id="createCourseButton" type="submit">CREA CORSO</button>
</form>
</body>
</html>

