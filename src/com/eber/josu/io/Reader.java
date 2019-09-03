package com.eber.josu.io;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;

public class Reader {

	static final long TICKS_AT_EPOCH = 621355968000000000L,
			                  TICKS_PER_MILLISECOND = 10000;
	
	private final DataInputStream in;
	private final int length;
	private boolean isClosed = false;
	
	protected Reader(FileInputStream inputStream) throws IOException{
		in = new DataInputStream(inputStream);
		length = in.available();
	}
	
	public long skip(long n) throws IOException {
		return in.skip(n);
	}
	
	public int skipBytes(int n) throws IOException {
		return in.skipBytes(n);
	}
	
	/**Closes the reader.*/
	public void close() throws IOException{
		if (!isClosed) {			
			isClosed = true;
			in.close();
		}
	}
	
	/**Indicates if the reader is closed.*/
	public boolean isClosed() {
		return isClosed;
	}

	public byte readByte() throws IOException{
		return in.readByte();
	}
	
	public short readShort() throws IOException{
		
		byte[] b = new byte[2];
		in.readFully(b);
		
		return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getShort();
		
	}
	
	public int readInt() throws IOException{
		
		byte[] b = new byte[4];
		in.readFully(b);

		return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
		
	}
	
	public long readLong() throws IOException{
		
		byte[] b = new byte[8];
		in.readFully(b);
		
		return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getLong();
		
	}
	
	public float readSingle() throws IOException{
		
		byte[] b = new byte[4];
		in.readFully(b);
		
		return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getFloat();
		
	}
	
	public double readDouble() throws IOException{
		
		byte[] b = new byte[8];
		in.readFully(b);
		
		return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getDouble();
		
	}
	
	public boolean readBoolean() throws IOException{
		return in.readBoolean();
	}
	
	protected int readULEB128() throws IOException{
		int value = 0;
		for (int shift = 0; shift < 32; shift += 7){
			byte b = readByte();
			value |= (b & 0x7F) << shift;
			if (b >= 0) return value;
		}
		throw new IOException("ULEB128 too large");
	}
	
	public int readStringLength() throws IOException {
		
		byte kind = readByte();
		
		if (kind == 0) return 0;
		if (kind != 0x0B) throw new IOException(String.format("String format error: Expected 0x0B or 0x00, found 0x%02X", kind & 0xFF));
		
		int length = readULEB128();
		
		return length;
		
	}
	
	public String readString() throws IOException{
		byte kind = readByte();
		
		if (kind == 0) return "";
		if (kind != 0x0B) throw new IOException(String.format("String format error: Expected 0x0B or 0x00, found 0x%02X", kind & 0xFF));
		
		int length = readULEB128();
		
		if (length == 0) return "";
		
		byte[] utf8Bytes = new byte[length];
		in.readFully(utf8Bytes);
		return new String(utf8Bytes, "UTF-8");
		
	}
	
	public Date readDate() throws IOException{
		long ticks = readLong();
		return new Date((ticks - TICKS_AT_EPOCH) / TICKS_PER_MILLISECOND);
	}
	
	public int getBytesRead() throws IOException {
		return length - in.available();
	}
	
}
