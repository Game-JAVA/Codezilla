import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TelaGameOver extends JFrame {
    // Método para carregar a fonte 8-bit ----------------------------------------------------------------------------
    private Font load8BitFont() {
        try {
            // Carrega a fonte 8-bit a partir do arquivo
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("sources/8-BIT WONDER.TTF"));
            return font;
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, 34); // Retorna uma fonte padrão em caso de erro
        }
    }

    // Construtor da classe LayoutGameOver
    public TelaGameOver() {
        // Configurações básicas da janela
        Sound overSound = new Sound("/sounds/gameOver.wav", false);
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
        mainPanel.setLayout(new GridBagLayout()); // Define o layout como GridBagLayout para melhor centralização
        mainPanel.setBackground(Color.BLACK); // Define a cor de fundo como preta

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(270, 10, 270, 10); // Define o espaçamento entre os componentes


        // Botão Reiniciar Jogo
        JButton restartButton = createStyledButton("Reiniciar Jogo");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação ao clicar no botão "Reiniciar Jogo"
                overSound.stop();
                new Run().setVisible(true); // Reinicia o jogo
                dispose();  // Fecha a tela de Game Over
            }
        });
        gbc.gridy = 1;
        mainPanel.add(restartButton, gbc); // Adiciona o botão ao painel principal

        // Botão Sair
        JButton exitButton = createStyledButton("Sair");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação ao clicar no botão "Sair"
                System.exit(0);  // Fecha o aplicativo
            }
        });
        gbc.gridy = 2;
        mainPanel.add(exitButton, gbc); // Adiciona o botão ao painel principal

        // Adiciona o painel principal à janela
        add(mainPanel);

        // Exibe a janela
        setVisible(true);
    }


    // Método para criar botões estilizados
    private JButton createStyledButton(String text) {
        JButton button = new RoundedButton(text); // Usa a classe RoundedButton para criar um botão com bordas arredondadas
        button.setFont(load8BitFont().deriveFont(Font.PLAIN, 24)); // Define a fonte do texto do botão
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
class GameOverBackgroundPanel extends JPanel {
    private Image backgroundImage;

    // Construtor que recebe a imagem de fundo
    public GameOverBackgroundPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

}

// Classe para criar botões com bordas arredondadas ----------------------------------------------------------------------
class RoundedButton1 extends JButton {
    private static final int RADIUS = 50; // Raio da borda arredondada
    public RoundedButton1(String text) {
        super(text);
        setContentAreaFilled(false); // Remove o preenchimento da área do conteúdo
        setFocusPainted(false); // Remove o foco do botão quando clicado
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
        return new Dimension(600, 600);
    }
}

