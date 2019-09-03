package com.eber.josu.io;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Writer implements Closeable{

	private static final Calendar CALENDAR = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	
	private DataOutputStream out;
	
	protected Writer(FileOutputStream output) {
		out = new DataOutputStream(output);
	}
	
	public void close() throws IOException {
		out.close();
	}
	
	public void write(byte b) throws IOException {
		out.writeByte(b);
	}
	
	public void write(byte[] b) throws IOException {
		out.write(b);
	}
	
	public void writeShort(short s) throws IOException {
		out.write(ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(s).array());
	}
	
	public void writeInt(int i) throws IOException {
		out.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array());
	}
	
	public void writeLong(long l) throws IOException {
		out.write(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(l).array());
	}
	
	public void writeSingle(float f) throws IOException {
		out.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(f).array());
	}
	
	public void writeDouble(double d) throws IOException {
		out.write(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(d).array());
	}
	
	public void writeBoolean(boolean b) throws IOException {
		out.writeBoolean(b);
	}
	
	private void writeULEB128(int i) throws IOException {
		int value = i;
		do {
			byte b = (byte) (value & 0x7F);
			value >>= 7;
			if (value != 0)
				b |= (1 << 7);
			out.writeByte(b);
		} while (value != 0);
	}
	
	public void writeString(String s) throws IOException {
		if (s == null || s.length() == 0) out.writeByte(0x00);
		else {
			out.writeByte(0x0B);
			writeULEB128(s.length());
			out.writeBytes(new String(s.getBytes(), "UTF-8"));
		}
		
	}
	
	public void write(Date date) throws IOException {

		CALENDAR.setTime(date);
		
		long ticks = Reader.TICKS_AT_EPOCH + CALENDAR.getTimeInMillis() * Reader.TICKS_PER_MILLISECOND;
		writeLong(ticks);
		
	}
	
}
