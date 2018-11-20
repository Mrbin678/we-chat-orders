<html>
<head>
    <meta charset="UTF-8">
    <title>信息提示</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="alert alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    错误!
                </h4> <strong>没有权限</strong><a href="https://www.imooc.com" class="alert-link"> 3S后自动跳转</a>
            </div>
        </div>
    </div>
</div>

</body>
<script>
    setTimeout('location.href="https://www.imooc.com"',3000);
</script>

</html>