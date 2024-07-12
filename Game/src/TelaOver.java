import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TelaGameOver extends JFrame {
    Sound overSound = new Sound("/sounds/gameOver.wav", false);

    // Método para carregar a fonte 8-bit ----------------------------------------------------------------------------
    private Font load8BitFont() {
        try {
            // Carrega a fonte 8-bit a partir do arquivo
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("sources/8-BIT WONDER.TTF"));
            return font;
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, 25); // Retorna uma fonte padrão em caso de erro
        }
    }

    // Construtor da classe LayoutGameOver
    public TelaGameOver() {
        // Configurações básicas da janela
        overSound.play();
        setSize(1920, 1080); // Define o tamanho da janela para 1920x1080 pixels
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define a operação de fechar a janela

        // Carregar a imagem de fundo
        ImageIcon backgroundIcon = null;
        try {
            // Tenta carregar a imagem de fundo a partir dos recursos
            backgroundIcon = new ImageIcon(this.getClass().getResource("images/Game OVER (2).gif"));
        } catch (Exception e) {
            // Imprime uma mensagem de erro se a imagem não for encontrada
            System.err.println("Erro ao carregar a imagem de fundo: " + e.getMessage());
        }

        // Painel principal com imagem de fundo
        BackgroundPanel mainPanel = new BackgroundPanel(backgroundIcon != null ? backgroundIcon.getImage() : null);
        mainPanel.setBackground(Color.BLACK); // Define a cor de fundo como preta
        mainPanel.setLayout(new BorderLayout()); // Define o layout como BorderLayout

        // Painel para os botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Torna o painel transparente
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 155, 0)); // Ajusta a borda para elevar os botões

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Define o espaçamento entre os componentes

        // Botão Reiniciar Jogo
        JButton restartButton = createStyledButton("Reiniciar");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação ao clicar no botão "Reiniciar Jogo"
                overSound.stop();
                new Run().setVisible(true); // Reinicia o jogo
                dispose();  // Fecha a tela de Game Over
            }
        });
        gbc.gridy = 0;
        buttonPanel.add(restartButton, gbc); // Adiciona o botão ao painel de botões

        // Botão Sair
        JButton exitButton = createStyledButton("Sair");
        exitButton.setPreferredSize(new Dimension(30, 30)); // Define o tamanho preferido do botão Sair
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação ao clicar no botão "Sair"
                System.exit(0);  // Fecha o aplicativo
            }
        });
        gbc.gridy = 1;
        buttonPanel.add(exitButton, gbc); // Adiciona o botão ao painel de botões

        // Adiciona o painel de botões à parte inferior do painel principal
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Adiciona o painel principal à janela
        add(mainPanel);

        // Exibe a janela
        setVisible(true);
    }

    // Método para criar botões estilizados
    private JButton createStyledButton(String text) {
        JButton button = new RoundedButton(text); // Usa a classe RoundedButton para criar um botão com bordas arredondadas
        button.setFont(load8BitFont().deriveFont(Font.PLAIN, 18)); // Define a fonte do texto do botão
        button.setForeground(Color.WHITE); // Define a cor do texto do botão como branco
        button.setBackground(new Color(0, 0, 0, 0));  // Define o fundo do botão como transparente
        button.setOpaque(false); // Torna o botão opaco
        button.setContentAreaFilled(false); // Remove o preenchimento da área do conteúdo
        button.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // Define a cor e espessura de pixels
        button.setFocusPainted(false); // Remove o foco do botão quando clicado

        // Adiciona efeitos de hover (passar o mouse sobre o botão)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Altera a cor do texto e da borda quando o mouse entra no botão
                button.setForeground(Color.RED);
                button.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Restaura a cor do texto e da borda quando o mouse sai do botão
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }
        });
        return button; // Retorna o botão estilizado
    }

    // Método principal para iniciar o programa
    public static void main(String[] args) {
        // Cria e exibe a tela de Game Over
        new TelaGameOver();
    }
}

// Classe para adicionar uma imagem de fundo ao painel
class BackgroundPanel2 extends JPanel {
    private Image backgroundImage;

    // Construtor que recebe a imagem de fundo
    public BackgroundPanel2(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

// Classe para criar botões com bordas arredondadas ----------------------------------------------------------------------
class RoundedButton2 extends JButton {
    private static final int RADIUS = 20; // Raio da borda arredondada

    public RoundedButton2(String text) {
        super(text);
        setContentAreaFilled(false); // Remove o preenchimento da área do conteúdo
        setFocusPainted(false); // Remove o foco do botão quando clicado
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
        super.paintComponent(g);
    }

    // Arrendondar borda dos botões
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
        g2.dispose();
    }

    // Definindo tamanho
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 40); // Ajuste o tamanho preferido conforme necessário
    }
}