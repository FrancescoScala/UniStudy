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

      $("#deleteCourseButton").click(chiamaControl);

      function chiamaControl() {

        var jqxhr = $.post("CourseControl",
                {
                  "action": $("#action").val()
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
  <input type="hidden" id="action" name="action" value="view">
  <button id="deleteCourseButton" type="submit">ELIMINA CORSO</button>
</form>
</body>
</html>

