package com.netty.demo.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class TestServerHandler extends SimpleChannelInboundHandler<String> {
    String TAG_LINE = "line.separator";

    private String TEST_LARGE_MSG = "当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "1.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "2.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "3.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "4.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "5.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "6.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "7.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "8.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "9.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "10.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "11.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "12.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "13.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "14.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "16.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "17.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "18.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "19.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "20.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "21.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "22.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "23.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "24.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "25.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "26.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "27.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "28.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "29.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "30.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "31.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "32.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "33.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "34.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "35.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "36.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "37.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "38.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "39.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "40.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "41.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "42.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "43.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "44.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "46.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "47.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "48.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "49.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "50.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "51.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "52.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "53.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "54.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "55.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "56.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "57.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "58.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）" +
            "59.当客户端数据量过大时，TCP协议会自动分包进行数据传输（何时分包，如何分包，每包大小尚未研究）， 使用netty做server时，netty会根据当前接收到的数据包大小（适用于当前连接），自动调整下次接收到数据包大小（TCP默认大小为1024，当数据包不超过1024时，会一次接收完毕，当超过1024时，下次自动增长为2048，然后下次增长为3072，然后下次增长为4096；然而，再往下增长，则增长了2048变为6144，然后变为8192，数据包增长到8192就已经是极限了（8KB），当客户端数据再大时，接收到的数据包也不再改变。此后，只要发送的数据包大小不超过8192，netty server一律按照一个包接收，不再分包）";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("server receive msg:" + s);

        channelHandlerContext.writeAndFlush("[reply]:" + TEST_LARGE_MSG + System.getProperty(TAG_LINE));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      /* cause.printStackTrace();
       ctx.close();*/

        Channel incoming = ctx.channel();
        if (!incoming.isActive())
            System.out.println("SimpleClient:" + incoming.remoteAddress()
                    + "异常");

        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 空闲次数
     */
    private int idle_count = 1;
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.READER_IDLE.equals(event.state())) {  //如果读通道处于空闲状态，说明没有接收到心跳命令
                System.out.println("  IdleState  READER_IDLE");
                System.out.println("已经5秒没有接收到客户端的信息了");
                if (idle_count > 2) {
                    System.out.println("关闭这个不活跃的channel");
                    ctx.channel().close();
                }
                idle_count++;
            }else if(IdleState.WRITER_IDLE.equals(event.state())){
                System.out.println("  IdleState  WRITER_IDLE");
            }else {
                System.out.println("  IdleState  ALL_IDLE");
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
