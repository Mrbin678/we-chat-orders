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
                                    <th>订单id</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>地址</th>
                                    <th>金额</th>
                                    <th>订单状态</th>
                                    <th>支付状态</th>
                                    <th>创建时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list orderDTOPage.content as orderDTO>
                                <tr>
                                    <td>${orderDTO.orderId}</td>
                                    <td>${orderDTO.buyerName}</td>
                                    <td>${orderDTO.buyerPhone}</td>
                                    <td>${orderDTO.buyerAddress}</td>
                                    <td>${orderDTO.orderAmount}</td>
                                    <td>${orderDTO.getOrderStatusEnum().msg}</td>
                                    <td>${orderDTO.getPayStatusEnum().msg}</td>
                                    <td>${orderDTO.createTime}</td>
                                    <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                                    <td>
                                            <#if orderDTO.getOrderStatusEnum().msg =="新订单">
                                                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
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
                                <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                            </#if>
                            <#-- 以下为带省略号分页 -->
                            <#--第一页-->
                            <#--如果总页数大于0-->
                            <#if (orderDTOPage.getTotalPages() > 0)>
                            <#-- 如果当前页码为1，即不可选 -->
                                <li <#if currentPage == 1>class="disabled"</#if>><a href="/sell/seller/order/list?page=1&size=${size}" >1</a></li>
                            </#if>

                            <#--如果不只有一页，总页数大于1-->
                            <#if (orderDTOPage.getTotalPages() > 1)>
                            <#--如果当前页往前查3页不是第2页，如果当前页码减去3，页码数任然大于2-->
                                <#if ((currentPage - 3) > 2)>
                                    <li><span class="text">…</span></li>
                                </#if>

                            <#--当前页的前3页和后3页，当前页码减去3到当前页码加上3 -->
                                <#list (currentPage - 3)..(currentPage + 3) as index>
                                <#--如果位于第一页和最后一页之间-->
                                    <#if (index > 1) && (index < orderDTOPage.getTotalPages())>
                                        <li <#if currentPage == index>class="disabled"</#if>><a href="/sell/seller/order/list?page=${index}&size=${size}" >${index}</a></li>
                                    </#if>
                                </#list>

                            <#--如果当前页往后查3页不是倒数第2页-->
                                <#if (currentPage + 3) < (orderDTOPage.getTotalPages() - 1)>
                                    <li><span class="text">…</span></li>
                                </#if>

                            <#--最后页-->
                                <li <#if currentPage == orderDTOPage.getTotalPages()>class="disabled"</#if>><a href="/sell/seller/order/list?page=${orderDTOPage.getTotalPages()}&size=${size}" >${orderDTOPage.getTotalPages()}</a></li>
                            </#if>

                            <#if currentPage gte orderDTOPage.getTotalPages()>
                                <li class="disabled"><a href="#">下一页</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                            </#if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <#--弹窗-->
        <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel">
                            提醒
                        </h4>
                    </div>
                    <div class="modal-body">
                        你有新的订单
                    </div>
                    <div class="modal-footer">
                        <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
                    </div>
                </div>

            </div>
        </div>

        <#--播放音乐(循环播放)，原生的html5-->
        <audio id="notice" loop="loop">
            <source src="/sell/mp3/William+Joseph+-+Radioactive.mp3" type="audio/mpeg">
        </audio>


        <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://cdn.bootcss.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>

        <script>
            var websocket = null;
            if('WebSocket' in window){
                //使用ws协议
                websocket = new WebSocket('ws://binbin678.natapp1.cc/sell/webSocket');
            }else {
                alert('该浏览器不支持websocket!');
            }
            //websocket事件
            //建立连接
            websocket.onopen = function (event) {
                console.log('建立连接');
            }
            //关闭连接
            websocket.onclose = function (event) {
                console.log('关闭连接');
            }
            //收到消息
            websocket.onmessage = function (event) {
                console.log('收到消息：'+event.data);
                //收到消息后的响应操作（例如：弹窗提醒，播放音乐）
                $('#myModal').modal('show');
                //google浏览器屏蔽掉了音乐的自动播放，在360浏览器上可以自动播放
                document.getElementById('notice').play();
            }
            //发生错误时的操作
            websocket.onerror = function () {
                alert('websocket通信发生错误!');
            }
            //窗口关闭的时候将websocket关闭
            window.onbeforeunload = function () {
                websocket.close();
            }
        </script>

    </body>
</html>

<#--<#list orderDTOPage.content as orderDTO>-->
    <#--${orderDTO.orderId}<br>-->

<#--</#list>-->




