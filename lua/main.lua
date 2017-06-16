require("luajava")

handler = luajava.newInstance("com.wonlake.core.ProtoBufClientAioHandler")
groupContext = luajava.newInstance("org.tio.client.ClientGroupContext", handler, nil)
node = luajava.newInstance("org.tio.core.Node", "127.0.0.1", 9090)
aioclient = luajava.newInstance("org.tio.client.AioClient", groupContext)
channel = aioclient:connect(node)

tio = luajava.bindClass("org.tio.core.Aio")
g = luajava.bindClass("com.wonlake.core.UtilSend")
p = g:build("mike", 22)
tio:send(channel, p)