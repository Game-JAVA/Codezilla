import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class LayoutTelaInicial extends JFrame {
    private Sound aberturaSound = new Sound("/sounds/abertura.wav", true);

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

    // Construtor da classe LayoutTelaInicial -------------------------------------------------------------------
    public LayoutTelaInicial() {
        aberturaSound.play();
        // Configurações básicas da janela
        setTitle("Menu Principal"); // Define o título da janela
        setSize(1920, 1080); // Define o tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define a operação de fechar a janela

        // Carregar a imagem de fundo ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        ImageIcon backgroundIcon = null;
        try {
            // Tenta carregar a imagem de fundo a partir dos recursos
            backgroundIcon = new ImageIcon(this.getClass().getResource("images/telaInicial.png"));
        } catch (Exception e) {
            // Imprime uma mensagem de erro se a imagem não for encontrada
            System.err.println("Erro ao carregar a imagem de fundo: " + e.getMessage());
        }

        // Painel principal com imagem de fundo
        BackgroundPanel mainPanel = new BackgroundPanel(backgroundIcon != null ? backgroundIcon.getImage() : null);
        mainPanel.setLayout(new GridBagLayout()); // Define o layout como GridBagLayout para melhor centralização
        mainPanel.setBackground(Color.BLACK); // Define a cor de fundo como preta

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Define o espaçamento entre os componentes

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        // Título do jogo
        JLabel titleLabel = new JLabel("STAR RAGE");
        titleLabel.setForeground(Color.ORANGE); // Define a cor do texto como laranja
        titleLabel.setFont(load8BitFont().deriveFont(Font.BOLD, 66)); // Define a fonte e o tamanho do texto
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza o título no eixo X
        mainPanel.add(titleLabel, gbc); // Adiciona o título ao painel principal


        //FUNÇÕES BOTÕES: ------------------------------------------------------------------------------------------------------------
        // Botão Iniciar Jogo
        JButton startButton = createRoundedButton("Iniciar Jogo");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação ao clicar no botão "Iniciar Jogo"
                aberturaSound.stop();
                new Run().setVisible(true); // Inicia o jogo
                dispose();  // Fecha o menu
            }
        });
        gbc.gridy = 1;
        mainPanel.add(startButton, gbc); // Adiciona o botão ao painel principal

        // Botão Sair
        JButton exitButton = createRoundedButton("Sair");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação ao clicar no botão "Sair"
                System.exit(0);  // Fecha o aplicativo
            }
        });
        gbc.gridy = 3;
        mainPanel.add(exitButton, gbc); // Adiciona o botão ao painel principal
        //--------------------------------------------------------------------------------------------
        // Adiciona o painel principal à janela
        add(mainPanel);

        // Exibe a janela
        setVisible(true);
    }

    // Método para criar botões arredondados estilizados -------------------------------------------------------------
    private JButton createRoundedButton(String text) {
        JButton button = new RoundedButton(text); // Usa a classe RoundedButton para criar um botão com bordas arredondadas
        button.setFont(load8BitFont().deriveFont(Font.PLAIN, 24)); // Define a fonte do texto do botão
        button.setBackground(new Color(0, 0, 0, 0));  // Define o fundo do botão como transparente
        button.setOpaque(false); // Torna o botão opaco
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false); // Remove o preenchimento da área do conteúdo
        button.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2)); // Define a borda do botão com cor e espessura
        button.setFocusPainted(false); // Remove o foco do botão quando clicado

        // Adiciona efeitos de hover (passar o mouse sobre o botão)
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                // Altera a cor do texto e da borda quando o mouse entra no botão
                button.setForeground(Color.ORANGE);
                button.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
            }

            public void mouseExited(MouseEvent evt) {
                // Restaura a cor do texto e da borda quando o mouse sai do botão
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }
        });
        return button; // Retorna o botão estilizado
    }

    // Método principal para iniciar o programa ----------------------------------------------------------------------
    public static void main(String[] args) {
        // Cria e exibe o menu inicial
        new LayoutTelaInicial();
    }
}

// Classe para adicionar uma imagem de fundo ao painel ----------------------------------------------------------------------
class BackgroundPanel extends JPanel {
    private Image backgroundImage;
    // Construtor que recebe a imagem de fundo
    public BackgroundPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Desenha a imagem de fundo se estiver disponível
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Caso contrário, preenche o fundo com a cor preta
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}

// Classe para criar botões com bordas arredondadas ----------------------------------------------------------------------
class RoundedButton extends JButton {
    private static final int RADIUS = 50; // Raio da borda arredondada
    public RoundedButton(String text) {
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
        return new Dimension(300, 60);
    }
}
