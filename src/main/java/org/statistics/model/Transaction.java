package org.statistics.model;


public class Transaction {
    private Double amount;
    private Long timestamp;

    public Transaction() {
    }

    public Transaction(Double amount, Long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("amount=").append(amount);
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}
