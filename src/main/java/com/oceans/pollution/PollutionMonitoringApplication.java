package com.oceans.pollution;

import com.oceans.pollution.database.DatabaseConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;

@SpringBootApplication
public class PollutionMonitoringApplication extends JFrame {


    public static void main(String[] args) {
        SpringApplication.run(PollutionMonitoringApplication.class, args);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PollutionMonitoringApplication app = new PollutionMonitoringApplication();
                app.setVisible(true);
            }
        });
    }

    public PollutionMonitoringApplication() {
        setTitle("Clean Oceans | Sistema de Monitoramento de Poluição Marinha");
        setSize(610, 670);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridBagLayout());
        painelPrincipal.setBackground(new Color(70, 130, 180));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/clean-oceans-logo.png")));
        Image logoImage = logoIcon.getImage().getScaledInstance(375, 185, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelPrincipal.add(logoLabel, gbc);

        RoundedButton btnColetaDados = createRoundedButton("Dados Coletados");
        btnColetaDados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirColetaDados();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelPrincipal.add(btnColetaDados, gbc);

        RoundedButton btnMonitoramento = createRoundedButton("Monitoramento em Tempo Real");
        btnMonitoramento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMonitoramentoTempoReal();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        painelPrincipal.add(btnMonitoramento, gbc);

        RoundedButton btnAnaliseDados = createRoundedButton("Análise de Dados");
        btnAnaliseDados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirAnaliseDados();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        painelPrincipal.add(btnAnaliseDados, gbc);

        add(painelPrincipal);
    }

    private RoundedButton createRoundedButton(String text) {
        RoundedButton button = new RoundedButton(text);
        button.setPreferredSize(new Dimension(250, 70));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(70, 130, 180));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(240, 240, 240));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });
        return button;
    }

    private void abrirColetaDados() {
        JFrame coletaFrame = new JFrame("Clean Oceans | Coleta de Dados");
        coletaFrame.setSize(600, 400);
        coletaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        coletaFrame.setLocationRelativeTo(null);
        coletaFrame.setResizable(false);

        String[] columnNames = {"ID", "Data da Coleta", "Localização", "Tipo de Plástico", "Quantidade"};
        Object[][] data = DatabaseConnection.getPlasticCollectionData();

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        coletaFrame.add(scrollPane);

        coletaFrame.setVisible(true);
    }

    private void abrirMonitoramentoTempoReal() {
        JFrame monitoramentoFrame = new JFrame("Clean Oceans | Monitoramento em tempo real");
        monitoramentoFrame.setSize(600, 400);
        monitoramentoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        monitoramentoFrame.setLocationRelativeTo(null);
        monitoramentoFrame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(70, 130, 180));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        RoundedButton btnTabela = createRoundedButton("Tabela de monitoramento");
        btnTabela.setPreferredSize(new Dimension(250, 70));
        btnTabela.setBackground(Color.WHITE);
        btnTabela.setForeground(new Color(70, 130, 180));
        btnTabela.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTabelaTempoReal();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(btnTabela, gbc);

        RoundedButton btnMapa = createRoundedButton("Área monitorada");
        btnMapa.setPreferredSize(new Dimension(250, 70));
        btnMapa.setBackground(Color.WHITE);
        btnMapa.setForeground(new Color(70, 130, 180));
        btnMapa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMapa();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(btnMapa, gbc);

        monitoramentoFrame.add(panel);
        monitoramentoFrame.setVisible(true);
    }

    private void abrirTabelaTempoReal() {
        JFrame tabelaFrame = new JFrame("Clean Oceans | Tabela de monitoramento em tempo real");
        tabelaFrame.setSize(600, 400);
        tabelaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tabelaFrame.setLocationRelativeTo(null);
        tabelaFrame.setResizable(false);


        String[] columnNames = {"ID", "Horário", "Localização", "Tipo de Plástico", "Quantidade"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tabelaFrame.add(scrollPane);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            final List<Object[]> dataList = List.of(DatabaseConnection.getPlasticCollectionData());
            int index = -1;
            final long initialTime = System.currentTimeMillis();

            @Override
            public void run() {
                if (index < dataList.size()) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            long currentTimeMillis = initialTime + (index * 5000L);
                            String formattedTime = new SimpleDateFormat("HH:mm:ss").format(new Date(currentTimeMillis));

                            Object[] newRow = new Object[]{dataList.get(index)[0], formattedTime, dataList.get(index)[2], dataList.get(index)[3], dataList.get(index)[4]};
                            tableModel.addRow(newRow);
                        }
                    });
                    index++;
                } else {
                    timer.cancel();
                }
            }
        }, 0, 5000);

        tabelaFrame.setVisible(true);
    }

    private void abrirMapa() {
        JFrame mapaFrame = new JFrame("Clean Oceans | Área monitorada");
        mapaFrame.setSize(610, 670);
        mapaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mapaFrame.setLocationRelativeTo(null);
        mapaFrame.setResizable(false);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 0, 0, 77));
                g2d.fillRect(getWidth() - 150, 0, 150, getHeight());
                g2d.dispose();
            }
        };
        panel.setBackground(new Color(70, 130, 180));
        panel.setLayout(new BorderLayout());

        ImageIcon mapIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/mapa-brasil.png")));
        JLabel mapLabel = new JLabel(new ImageIcon(mapIcon.getImage().getScaledInstance(560, 582, Image.SCALE_SMOOTH)));
        panel.add(mapLabel, BorderLayout.CENTER);

        mapaFrame.add(panel);
        mapaFrame.setVisible(true);
    }

    private void abrirAnaliseDados() {
        JFrame analiseFrame = new JFrame("Clean Oceans | Análise de Dados");
        analiseFrame.setSize(610, 670);
        analiseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        analiseFrame.setLocationRelativeTo(null);
        analiseFrame.getContentPane().setBackground(new Color(70, 130, 180));
        analiseFrame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(70, 130, 180));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        RoundedButton btnGrafico1 = createRoundedButton("Tipo de plástico x Quantidade encontrada");
        btnGrafico1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirGrafico("Clean Oceans | Tipo de plástico x Quantidade encontrada", "/images/plastico-x-quantidade.png");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(btnGrafico1, gbc);

        RoundedButton btnGrafico2 = createRoundedButton("Locais x Quantidade encontrada");
        btnGrafico2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirGrafico("Clean Oceans | Locais x Quantidade encontrada", "/images/locais-x-quantidade.png");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(btnGrafico2, gbc);

        RoundedButton btnGrafico3 = createRoundedButton("Previsão para diminuição");
        btnGrafico3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirGrafico("Clean Oceans | Previsão para diminuição", "/images/previsao-diminuição-plasticos.png");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(btnGrafico3, gbc);

        analiseFrame.add(panel);
        analiseFrame.setVisible(true);
    }

    private void abrirGrafico(String title, String imagePath) {
        JFrame graficoFrame = new JFrame(title);
        graficoFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        graficoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        graficoFrame.setLocationRelativeTo(null);
        graficoFrame.getContentPane().setBackground(new Color(70, 130, 180));
        graficoFrame.setResizable(false); //

        ImageIcon graficoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
        JLabel graficoLabel = new JLabel(new ImageIcon(graficoIcon.getImage()));
        graficoFrame.add(graficoLabel);

        graficoFrame.setVisible(true);
    }

    static class RoundedButton extends JButton {
        @Serial
        private static final long serialVersionUID = 1L;

        public RoundedButton(String label) {
            super(label);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g2);
            g2.dispose();
        }

        @Override
        public void setContentAreaFilled(boolean b) {
        }
    }
}
