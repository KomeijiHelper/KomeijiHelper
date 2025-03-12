# 基于WebSocket的通信框架

使用时需要确定两部分

1. 连接建立的url
2. 如何解析websocket中的文本流和二进制流

所有的impl包中均可以提供自己的实现
每个package内容如下
- channel: WebSocket的具体连接相关，概念同netty中的channel
- message: 发送的消息以及消息转发队列
- protocol: 文本流和二进制流的解析协议
- session: 与服务器建立连接时的会话

## 例子
以聊天系统为例，user1.html中
` socket = new WebSocket("ws://localhost:54950/chat?from=test1&to=test2");`
建立了一个test1向test2发送消息的连接
这里的约束即为连接的url格式为`chat?from=xxx&to=xxx`

其中`OneWaySession`是每个用户与服务器建立的单向会话（区别用户之间建立的聊天会话，此处的会话用于与服务器交互，因此单向指的是用户之间的单向）

在`DefaultTextFrameProtocol`中规定了文本流为形如`{"type":xxx,"content":xxx}`的json
在转发消息时也需要实现对应的`Message`具体类`TextMessage`
以及转发时的具体操作`MessageHandler`