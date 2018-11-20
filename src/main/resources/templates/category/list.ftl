<html>
    <#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
<#--边栏 sidebar-->
            <#include "../common/nav.ftl">
<#--主要内容区域 content-->
    <div id="page-content-wrapper" >
        <div class="container-fluid">
            <div class="row-fluid">
                <div class="span12">
                    <table class="table table-bordered table-hover table-condensed">
                        <thead>
                        <tr>
                            <th>类目id</th>
                            <th>名字</th>
                            <th>type</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                                <#list categoryList as category>
                                <tr>
                                    <td>${category.categoryId}</td>
                                    <td>${category.categoryName}</td>
                                    <td>${category.categoryType}</td>
                                    <td>${category.createTime}</td>
                                    <td>${category.updateTime}</td>
                                    <td><a href="/sell/seller/category/index?categoryId=${category.categoryId}">修改</a></td>
                                </tr>
                                </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


</body>

</html>

<#--<#list orderDTOPage.content as orderDTO>-->
<#--${orderDTO.orderId}<br>-->

<#--</#list>-->