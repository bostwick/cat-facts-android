package okhttp3

import java.net.InetAddress
import java.net.Socket
import javax.net.SocketFactory

/**
 * A {@link SocketFactory} that delegates calls. Sockets can be configured after creation by
 * overriding {@link #configureSocket(java.net.Socket)}.
 */
open class DelegatingSocketFactory(val delegate: SocketFactory) : SocketFactory() {

    override fun createSocket() = delegate.createSocket().also {
        configureSocket(it)
    }

    override fun createSocket(p0: String?, p1: Int) = delegate.createSocket(p0, p1).also {
        configureSocket(it)
    }


    override fun createSocket(p0: String?, p1: Int, p2: InetAddress?, p3: Int) =
            delegate.createSocket(p0, p1, p2, p3).also {
                configureSocket(it)
            }

    override fun createSocket(p0: InetAddress?, p1: Int) = delegate.createSocket(p0, p1).also {
        configureSocket(it)
    }

    override fun createSocket(p0: InetAddress?, p1: Int, p2: InetAddress?, p3: Int) =
            delegate.createSocket(p0, p1, p2, p3).also {
                configureSocket(it)
            }

    open fun configureSocket(socket: Socket): Socket {
        // No-op by default.
        return socket;
    }
}
