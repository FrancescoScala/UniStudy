<%--
  Created by IntelliJ IDEA.
  User: nickm
  Date: 06/02/2023
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script
            src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
    </script>

    <title>Elimina corso</title>

    <script type="text/javascript">
        $(document).ready(function () {
            viewCourses();
            $("#deleteCourseButton").click(chiamaControl);

            function viewCourses() {
                $("#courses").find('option').remove();
                var jqxhr = $.post("CourseControl",
                    {
                        "action": "view"
                    },
                    function (data) {
                        let objects = data.objects;
                        for (let i = 0; i < objects.length; i++) {
                            let object = objects[i];
                            $("#courses").append("<option value=" + object["id"] + ">" + object["title"] + "</option>");
                        }
                    });
            }

            function chiamaControl() {
                var jqxhr1 = $.post("CourseControl",
                    {
                        "action": $("#action").val(),
                        "id": $("#courses option:selected").val()
                    },
                    function (data) {
                        var success = "\"OK\"";
                        var result = (JSON.stringify(data.result)).localeCompare(success);

                        if (result == 0) {
                            $("#mex").html("Rimozione effettuata");
                        } else {
                            $("#mex").html(data.result);
                        }
                        viewCourses();
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
    <select id="courses"></select>
    <input type="hidden" id="action" name="action" value="delete">
    <button id="deleteCourseButton" type="submit">ELIMINA CORSO</button>
</form>
</body>
</html>

