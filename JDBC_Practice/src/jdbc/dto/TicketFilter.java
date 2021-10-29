package jdbc.dto;

public class TicketFilter {
	private int limit;
	private int offset;
	
	public TicketFilter() {
		// TODO Auto-generated constructor stub
	}
	
	public TicketFilter(int limit, int offset) {
		this.limit = limit;
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public int getOffset() {
		return offset;
	}

	@Override
	public String toString() {
		return "TicketFilter [limit=" + limit + ", offset=" + offset + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + limit;
		result = prime * result + offset;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TicketFilter other = (TicketFilter) obj;
		if (limit != other.limit)
			return false;
		if (offset != other.offset)
			return false;
		return true;
	}
	
}
