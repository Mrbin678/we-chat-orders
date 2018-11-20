<html>
    <#include "../common/header.ftl">

<body>

<div id="wrapper" class="toggled">
    <#--边栏 sidebar-->
    <#include "../common/nav.ftl">
    <#--主要内容区域 content-->
    <div id="page-content-wrapper" >
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-6 column">
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label>名字</label>
                            <input name="categoryName" type="text" class="form-control" value="${(category.categoryName)!''}" />
                        </div>
                        <div class="form-group">
                            <label>type</label>
                            <input name="categoryType" type="number" class="form-control" value="${(category.categoryType)!''}" />
                        </div>
                        <#--hidden 隐藏表单域，提交需要修改的商品id-->
                        <input hidden type="text" name="categoryId" value="${(category.categoryId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>

</html>

