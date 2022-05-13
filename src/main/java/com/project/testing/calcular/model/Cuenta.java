package com.project.testing.calcular.model;

import com.project.testing.calcular.exception.InsuficientMoneyException;
import java.math.BigDecimal;
import java.util.Objects;

public class Cuenta {

    private String persona;
    private BigDecimal saldo;
    private Banco banco;

    public Cuenta(String persona, BigDecimal saldo, Banco banco){
        this.persona = persona;
        this.saldo = saldo;
        this.banco = banco;
    }

    public Cuenta(String persona, BigDecimal saldo){
        this.persona = persona;
        this.saldo = saldo;
    }

    public void debito(BigDecimal monto){
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);

        if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 1){
            throw new InsuficientMoneyException("Dinero insuficiente.");
        }

        this.saldo = saldo.subtract(monto);

    }

    public void credito(BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }

    public String getPersona() {
        return persona;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(this.persona, cuenta.persona) && Objects.equals(this.saldo, cuenta.saldo) && Objects.equals(this.banco, cuenta.banco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persona, saldo, banco);
    }
}
