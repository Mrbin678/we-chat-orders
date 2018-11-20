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
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="productName" type="text" class="form-control" value="${(productInfo.productName)!''}" />
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input name="productPrice" type="text" class="form-control" value="${(productInfo.productPrice)!''}" />
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input name="productStock" type="number" class="form-control" value="${(productInfo.productStock)!''}" />
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)!''}" />
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img height="200" width="200" src="${(productInfo.productIcon)!''}">
                            <input name="productIcon" type="text" class="form-control" value="${(productInfo.productIcon)!''}" />
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <#--这里进行商品类目的判断，商品类目存在并且和数据库中遍历的某个类目相同就让它被选中，
                                    这一系列判断只为了判断select这个关键字该不该加上去-->
                                    <option value="${category.categoryType}"
                                        <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                            selected
                                        </#if>
                                    >${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <#--hidden 隐藏表单域，提交需要修改的商品id-->
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>

</html>

