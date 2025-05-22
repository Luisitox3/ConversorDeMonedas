import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ConversorMonedasGUI extends JFrame {
    private JComboBox<String> comboOrigen;
    private JComboBox<String> comboDestino;
    private JTextField txtCantidad;
    private JLabel lblResultado;

    private final String[] monedas = {"USD", "EUR", "GBP", "JPY", "MXN", "BRL"};

    public ConversorMonedasGUI() {
        setTitle(" Conversor de Monedas");
        setSize(650, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel principal con borde
        JPanel panelCentral = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCentral.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Seleccione la moneda de origen y destino e ingrese la cantidad:",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14), Color.DARK_GRAY
        ));
        panelCentral.setBackground(new Color(245, 245, 245));

        comboOrigen = new JComboBox<>(monedas);
        comboDestino = new JComboBox<>(monedas);
        txtCantidad = new JTextField();
        lblResultado = new JLabel("Resultado: ", SwingConstants.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 16));
        lblResultado.setForeground(new Color(0, 102, 102));

        JButton btnConvertir = new JButton("Convertir");
        btnConvertir.setBackground(new Color(0, 153, 204));
        btnConvertir.setForeground(Color.WHITE);
        btnConvertir.setFocusPainted(false);
        btnConvertir.setFont(new Font("Arial", Font.BOLD, 14));

        // Agregamos los campos al panel
        panelCentral.add(new JLabel("Moneda de origen:", SwingConstants.RIGHT));
        panelCentral.add(comboOrigen);

        panelCentral.add(new JLabel("Moneda de destino:", SwingConstants.RIGHT));
        panelCentral.add(comboDestino);

        panelCentral.add(new JLabel("Cantidad a convertir:", SwingConstants.RIGHT));
        panelCentral.add(txtCantidad);

        panelCentral.add(new JLabel()); // vacío
        panelCentral.add(btnConvertir);

        add(panelCentral, BorderLayout.CENTER);
        add(lblResultado, BorderLayout.SOUTH);

        btnConvertir.addActionListener(this::convertirMoneda);

        setVisible(true);
    }

    private void convertirMoneda(ActionEvent e) {
        String origen = (String) comboOrigen.getSelectedItem();
        String destino = (String) comboDestino.getSelectedItem();

        if (origen.equals(destino)) {
            JOptionPane.showMessageDialog(this, "Las monedas deben ser distintas.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double cantidad = Double.parseDouble(txtCantidad.getText());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La Cantidad debe ser mayor que cero.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double tasa = ConversorMonedas.obtenerTasaCambio(origen, destino);
            if (tasa > 0) {
                double resultado = cantidad * tasa;
                lblResultado.setText(String.format("Resultado: %.2f %s = %.2f %s",
                        cantidad, origen, resultado, destino));
            } else {
                lblResultado.setText("No se pudo obtener la tasa de cambio.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa una cantidad. (número).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConversorMonedasGUI::new);
    }
}
