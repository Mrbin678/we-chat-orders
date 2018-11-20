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
                                    <th>商品id</th>
                                    <th>名称</th>
                                    <th>图片</th>
                                    <th>单价</th>
                                    <th>库存</th>
                                    <th>描述</th>
                                    <th>类目</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list productInfoPage.content as productInfo>
                                <tr>
                                    <td>${productInfo.productId}</td>
                                    <td>${productInfo.productName}</td>
                                    <td><img height="100" width="100" src="${productInfo.productIcon}"></td>
                                    <td>${productInfo.productPrice}</td>
                                    <td>${productInfo.productStock}</td>
                                    <td>${productInfo.productDescription}</td>
                                    <td>${productInfo.categoryType}</td>
                                    <td>${productInfo.createTime}</td>
                                    <td>${productInfo.updateTime}</td>
                                    <td><a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a></td>
                                    <td>
                                        <#if productInfo.getProductStatusEnum().message =="在架">
                                            <a href="/sell/seller/product/off_sale?productId=${productInfo.productId}">下架</a>
                                        <#else>
                                            <a href="/sell/seller/product/on_sale?productId=${productInfo.productId}">上架</a>
                                        </#if>
                                    </td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    <#--分页-->
                    <#--   freemaker中使用lt、lte、gt和gte来替代<、<=、>和>=   -->
                        <div class="col-md-12 column">
                            <ul class="pagination pull-right">
                            <#-- 当前页码如果小于等于1，页码设置为不可选，即：disabled,否则 上一页 就是 当前页码减去1 -->
                            <#if currentPage lte 1>
                                <li class="disabled"><a href="#">上一页</a></li>
                            <#else>
                                <li><a href="/sell/seller/product/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                            </#if>
                            <#-- 以下为带省略号分页 -->
                            <#--第一页-->
                            <#--如果总页数大于0-->
                            <#if (productInfoPage.getTotalPages() > 0)>
                            <#-- 如果当前页码为1，即不可选 -->
                                <li <#if currentPage == 1>class="disabled"</#if>><a href="/sell/seller/product/list?page=1&size=${size}" >1</a></li>
                            </#if>

                            <#--如果不只有一页，总页数大于1-->
                            <#if (productInfoPage.getTotalPages() > 1)>
                            <#--如果当前页往前查3页不是第2页，如果当前页码减去3，页码数任然大于2-->
                                <#if ((currentPage - 3) > 2)>
                                    <li><span class="text">…</span></li>
                                </#if>

                            <#--当前页的前3页和后3页，当前页码减去3到当前页码加上3 -->
                                <#list (currentPage - 3)..(currentPage + 3) as index>
                                <#--如果位于第一页和最后一页之间-->
                                    <#if (index > 1) && (index < productInfoPage.getTotalPages())>
                                        <li <#if currentPage == index>class="disabled"</#if>><a href="/sell/seller/product/list?page=${index}&size=${size}" >${index}</a></li>
                                    </#if>
                                </#list>

                            <#--如果当前页往后查3页不是倒数第2页-->
                                <#if (currentPage + 3) < (productInfoPage.getTotalPages() - 1)>
                                    <li><span class="text">…</span></li>
                                </#if>

                            <#--最后页-->
                                <li <#if currentPage == productInfoPage.getTotalPages()>class="disabled"</#if>><a href="/sell/seller/product/list?page=${productInfoPage.getTotalPages()}&size=${size}" >${orderDTOPage.getTotalPages()}</a></li>
                            </#if>

                            <#if currentPage gte productInfoPage.getTotalPages()>
                                <li class="disabled"><a href="#">下一页</a></li>
                            <#else>
                                <li><a href="/sell/seller/product/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                            </#if>
                            </ul>
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