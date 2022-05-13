package com.project.testing.calcular.model;

import com.project.testing.calcular.exception.InsuficientMoneyException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assumptions.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CuentaTest {

    Cuenta cuenta0;
    Cuenta cuenta1;

    @BeforeAll
    static void beforeAll(){
        System.out.println("Inicializamos el test.");
    }

    @AfterAll
    static void afterTest(){
        System.out.println("Finalizando el test.");
    }

    @BeforeEach
    void initMetodoTest(){
        this.cuenta0 = new Cuenta("Andres", new BigDecimal("1000.12345"));
        this.cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));
        System.out.println("Iniciando el método...");
    }

    @AfterEach
    void tearDown(){
        System.out.println("Finalizando el método.");
    }

    @Tag("Cuenta")
    @Test
    @DisplayName("Probando que el nombre del dueño de la cuenta no sea null, que sea igual al esperado.")
    void testNombreCuenta() {
        String real = cuenta0.getPersona();
        assertNotNull(real, "La cuenta no puede ser nula.");
        assertEquals("Andres",real,"El nombre de la cuenta no es el que se esperaba");
    }

    @Test
    @DisplayName("Test Saldo Cuenta Dev")
    void testSaldoCuenta(){
        assertNotNull(cuenta0.getSaldo());
        assertEquals(1000.12345, cuenta0.getSaldo().doubleValue());
        assertFalse(cuenta0.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    @DisplayName("Probando que dos cuentas con los mismos datos se identifiquen como iguales.")
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));

        assertEquals(cuenta2,cuenta);
    }

    @Test
    @DisplayName("probando que el saldo no sea nulo y que sea igual al esperado después de hacer un débito.")
    void testDebitoCuenta() {
        cuenta0.debito(new BigDecimal(100));
        assertNotNull(cuenta0.getSaldo());
        assertEquals("900.12345", cuenta0.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Probando que el saldo no sea nulo y que sea igual al esperado después de hacer un crédito.")
    void testCreditoCuenta() {
        cuenta0.credito(new BigDecimal(100));
        assertNotNull(cuenta0.getSaldo());
        assertEquals("1100.12345", cuenta0.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Probando que se lance una excepción cuando no hay dinero en la cuenta.")
    void testInsuficientMoneyException() {
        Exception ex = assertThrows(InsuficientMoneyException.class, ()-> cuenta0.debito(new BigDecimal(2000)));
        String actual = ex.getMessage();
        String expected = "Dinero insuficiente.";
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Probando que al hacer una transferencia entre dos cuentas, ambas tengan el saldo esperado.")
    void testTransferirDinero() {
        Banco banco = new Banco();
        banco.setNombre("Banco del estado");
        banco.transferir(cuenta0,cuenta1,new BigDecimal(500));
        assertEquals("500.12345", cuenta0.getSaldo().toPlainString());
        assertEquals("9400.9997", cuenta1.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Probando que las cuentas tengan el saldo asignado, el banco tenga las cuentas, el banco de la cuenta sea correcto y cuentas con los nombres correctos")
    void testRelacionBancoCuentas() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));
        Banco banco = new Banco();
        banco.setNombre("Banco del estado");
        banco.addCuenta(cuenta0);
        banco.addCuenta(cuenta1);
        assertAll(() -> {assertEquals("1000.12345", cuenta0.getSaldo().toPlainString());},
                () -> {assertEquals("8900.9997", cuenta1.getSaldo().toPlainString());},
                () -> {assertEquals(2,banco.getCuentas().size());},
                () -> {assertEquals("Banco del estado", cuenta0.getBanco().getNombre());},
                () -> {assertEquals("Andres", banco.getCuentas().stream()
                        .filter(c -> c.getPersona().equals("Andres"))
                        .findFirst().get().getPersona());},
                () -> {assertTrue(banco.getCuentas().stream()
                        .anyMatch(c -> c.getPersona().equals("Jhon Doe")));});
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void soloWindows(){

    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void soloLinuxMac(){

    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void testNoWindows(){

    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void testSoloJava8(){

    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void testSoloJava17(){

    }

    @Test
    @DisabledOnJre(JRE.JAVA_17)
    void testNoJava17(){

    }

    @Test
    void printSystemProperties(){
        Properties properties = System.getProperties();
        properties.forEach((k,v) -> System.out.println(k + ":" + v));
    }

    @Test
    @EnabledIfSystemProperty(named = "java.version", matches = "17")
    void testJavaVersion(){

    }

    @Test
    @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
    void solo64(){

    }

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
    void solo32(){

    }

    @Test
    @DisabledIfSystemProperty(named = "user.name", matches = ".*cristiadiaz.*")
    void soloUserName(){

    }

    @Test
    void printEnvironmentVariables(){
        Map<String, String> getEnv = System.getenv();
        getEnv.forEach((k,v) -> System.out.println(k + " = " + v));
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = "/Users/cristiadiaz/.sdkman/candidates/java/current")
    void testJavaHome(){

    }

    @Test
    @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "6")
    void numberProcessors(){

    }

    @ParameterizedTest(name = "{index}")
    @ValueSource(strings = {"100", "200", "300", "500", "700", "1000"})
    @DisplayName("probando que el saldo no sea nulo y que sea igual al esperado después de hacer un débito.")
    void testDebitoCuentaWithParams(String monto) {
        cuenta0.debito(new BigDecimal(monto));
        assertNotNull(cuenta0.getSaldo());
        assertTrue(cuenta0.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @ParameterizedTest
    @CsvSource({"200, 100", "250,200", "301, 300", "501, 500", "750, 700", "1001.12345, 1000.12345"})
    @DisplayName("probando que el saldo no sea nulo y que sea igual al esperado después de hacer un débito.")
    void testDebitoCuentaWithParamsCsv(String saldo, String monto) {
        cuenta0.setSaldo(new BigDecimal(saldo));
        cuenta0.debito(new BigDecimal(monto));
        assertNotNull(cuenta0.getSaldo());
        assertTrue(cuenta0.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Tag("param")
    @ParameterizedTest
    @MethodSource("montoList")
    @DisplayName("probando que el saldo no sea nulo y que sea igual al esperado después de hacer un débito.")
    void testDebitoCuentaWithParamsMethodSource(String monto) {
        cuenta0.debito(new BigDecimal(monto));
        assertNotNull(cuenta0.getSaldo());
        assertTrue(cuenta0.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    private static List<String> montoList(){
        return Arrays.asList("100","200","300","500","700","1000");
    }

    @Test
    @DisplayName("Test Saldo Cuenta Dev")
    void testSaldoCuenta1(){
        boolean esDev = "dev".equals(System.getProperty("ENV"));
        assumeTrue(esDev);
        assertNotNull(cuenta0.getSaldo());
        assertEquals(1000.12345, cuenta0.getSaldo().doubleValue());
        assertFalse(cuenta0.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    @DisplayName("Test Saldo Cuenta Dev 2")
    void testSaldoCuenta2(){
        boolean esDev = "dev".equals(System.getProperty("ENV"));
        assumingThat(esDev, () -> {
            assertNotNull(cuenta0.getSaldo());
            assertEquals(1000.12345, cuenta0.getSaldo().doubleValue());
        });


        assertFalse(cuenta0.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

}