import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.time.*;

@SuppressWarnings("serial")
public class Run extends javax.swing.JFrame implements Runnable{

    // tamanho da tela
    private int height, width, score = 0;

    // Variável para controlar o estado de pausa
    private boolean isPaused = false;

    // teclas
    /*
     * Funcionamento: quando a tecla é pressionada sua respectiva variável fica
     * com valor True. Quando solta a variável fica com valor False.
     */
    private boolean left, right, up, down, shoot, aux = false, soundDeath = false, mediumDeath = true;
    // Millis (função de tempo) para fazer o delay dos tiros
    Clock clock = Clock.systemDefaultZone();
    long millisPlayer = clock.millis(), milisPlayer2;
    long millisSmall = clock.millis(), millisSmall2;
    long millisMedium = clock.millis(), millisMedium2;
    long millisHorda = clock.millis(), millisHorda2;
    long millisHorda3 = clock.millis(), millisHorda4;
    long millisDeath = clock.millis(), millisDeath2;
    byte i = 0;
    short timeInimigo = 1000;

    // Construtor
    public Run() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = gd.getDisplayMode().getWidth();
        height = gd.getDisplayMode().getHeight();


        // Chama o metodo que realiza todas as configurações iniciais necessárias
        initComponents();

        // Mecanismo de execução paralela
        createBufferStrategy(2);
        Thread t = new Thread(this);
        t.start();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // Realiza todas as configurações iniciais necessárias
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Chama os métodos de Scaneamento de teclas (tecla pressionada / solta)
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        // Configura o layout da tela
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        // Largura
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, width, Short.MAX_VALUE)
        );
        // Altura
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, height, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Método que verifica se as teclas foram pressionadas
    private void formKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_A) {
            left = true;
        } else if (evt.getKeyCode() == KeyEvent.VK_D) {
            right = true;
        } else if (evt.getKeyCode() == KeyEvent.VK_W) {
            up = true;
        } else if (evt.getKeyCode() == KeyEvent.VK_S) {
            down = true;
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            shoot = true;
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            isPaused = !isPaused; // Alterna o estado de pausa --------------------------------------------
            if (isPaused) {
                showPauseScreen();
            }
        }
    }
    // Método para exibir a tela de pausa -------------------------------------------------------------------
    private void showPauseScreen() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TelaPause().setVisible(true);
            }
        });
    }
//-----------------------------------------------------------------------------------------------------


    // Método que verifica se as teclas foram soltas
    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_A) {
            left = false;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_D) {
            right = false;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_W) {
            up = false;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_S) {
            down = false;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            shoot = false;
        }
    }//GEN-LAST:event_formKeyReleased

    // Inicializa a janela
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Run().setVisible(true);
            }
        });
    }

    @Override
    public void run() {
        Graphics g = getBufferStrategy().getDrawGraphics();
        boolean animation = true;

        // Objeto da imagem de fundo
        ImageIcon img = new ImageIcon(this.getClass().getResource("/images/fundo.gif.gif"));

        // Objetos das imagens do score
        ImageIcon imgDec, imgUni, imgScore;

        // Objeto da imagem de explosão do player
        ImageIcon imgExplosion = new ImageIcon(this.getClass().getResource("/images/explosion2.gif"));

        // Objeto do som do tiro
        Sound shootSound = new Sound("/sounds/tiro.wav", false);
        // Objeto do som de explosão do inimigo
        Sound explosionInimigo = new Sound("/sounds/explosionSmall.wav", false);
        // Objeto do som de explosão do player
        Sound explosionPlayer = new Sound("/sounds/explosionPlayer.wav", false);

        // ArrayList dos inimigos e tiros, pois irá ter vários
        ArrayList<Shoot> shootsPlayer = new ArrayList<>();
        ArrayList<Shoot> shootsInimigo = new ArrayList<>();
        ArrayList<Inimigo> inimigos = new ArrayList<>();
        ArrayList<Explosion> explosões = new ArrayList<>();

        Player n = new Player(width/2, height, 10,8, 10, 0.2, "/images/nave_player.gif");

        while(true) {
            // Atualiza g
            g = getBufferStrategy().getDrawGraphics();

            // Limpa tela
            g.clearRect(0, 0, getWidth(), getHeight());

            // Se o jogo estiver pausado, não executa a lógica do jogo
            if (isPaused)
                continue;

            // Pontuação
            int uni = score % 10;
            int dec = score/10;
            imgDec = new ImageIcon(this.getClass().getResource("/images/score/" + (char) (dec + '0') + ".png"));
            imgUni = new ImageIcon(this.getClass().getResource("/images/score/" + (char) (uni + '0') + ".png"));
            imgScore = new ImageIcon(this.getClass().getResource("/images/score/score.png"));

            // Animação da tela de fundo
            g.drawImage(img.getImage(), 0, 0, width + 50, height, null);

            // Animação da pontuação
            g.drawImage(imgDec.getImage(), 20, 80, (int) (imgDec.getIconWidth() * 0.2), (int) (imgDec.getIconHeight() * 0.2), null);
            g.drawImage(imgUni.getImage(), 50, 80, (int) (imgUni.getIconWidth() * 0.2), (int) (imgUni.getIconHeight() * 0.2), null);
            g.drawImage(imgScore.getImage(), 32, 60, (int) (imgUni.getIconWidth() * 0.22), (int) (imgUni.getIconHeight() * 0.08), null);

            // Animação da nave player
            n.draw(g);

            if (n.isDead()) {
                if(!soundDeath) {
                    explosionPlayer.play();
                    soundDeath = true;
                }
                millisDeath = clock.millis();
                if((millisDeath - millisDeath2) < 400)
                    g.drawImage(imgExplosion.getImage(), n.getX(), n.getY(), (int) (imgExplosion.getIconWidth() * 0.5), (int) (imgExplosion.getIconHeight() * 0.5), null);
                else if((millisDeath - millisDeath2) > 1500) {
                    new TelaGameOver();
                    dispose(); // Fecha a tela de jogo atual
                    break;
                }
            }

            // Animação Inicial
            if (animation && n.getY() >= 800) {
                n.animation();
                millisHorda = clock.millis();
                millisHorda2 = millisHorda;
                millisMedium = clock.millis();
                millisMedium2 = millisMedium;
            }
            else {
                animation = false;

                // Horda de inimigos
                horda(inimigos, g);

                // Controles
                if (right)
                    n.moveX(1);
                if (left)
                    n.moveX(-1);
                if (down)
                    n.moveY(1);
                if (up)
                    n.moveY(-1);

                // Tiro do player a cada 300ms
                millisPlayer = clock.millis();

                if ((millisPlayer - milisPlayer2) > 300 && shoot) {
                    shootsPlayer.add(new Shoot(n.getX(), n.getY(), 10, 9, "/images/tiro.gif", height, width, 0.4, false, n.getWidth()));
                    shootsPlayer.add(new Shoot(n.getX(), n.getY(), 10, 9, "/images/tiro.gif", height, width, 0.4, true, n.getWidth()));
                    shootSound.play();
                    milisPlayer2 = millisPlayer;
                }

                // Tiros dos inimigos a cada 2,5s
                for (int i = 0; i < inimigos.size(); i++) {
                    Inimigo n2 = inimigos.get(i);
                    if ((n2.getmillisShoot() - n2.getmillisShoot2()) > 2800) {
                        if(n2.getType() == 1)
                            shootsInimigo.add(new Shoot(n2.getX(), n2.getY(), 10, 5, "/images/tiroHorda.png", height, width, 0.04, false, n.getWidth()));
                        else if(n2.getType() == 0)
                            shootsInimigo.add(new Shoot(n2.getX(), n2.getY(), 10, 5, "/images/tiroInimigo.png", height, width, 0.06, false, n.getWidth()));
                        else
                            shootsInimigo.add(new Shoot(n2.getX(), n2.getY(), 10, 5, "/images/tiroMedium.png", height, width, 0.07, false, n.getWidth()));
                        n2.setmillisShoot();
                    }
                }

                // Animação dos tiros
                // Tiros do Player
                for (int i = 0; i < shootsPlayer.size(); i++) {
                    // Varro o array de tiros do player para desenhar eles
                    Shoot s = shootsPlayer.get(i);
                    s.draw(g);
                    // Movo eles para cima e, caso passem da tela, excluo do array
                    if (s.move()) {
                        System.out.println("Tiro do player removido");
                        shootsPlayer.remove(i);
                        i--;
                        continue;
                    }
                    // Varro o array de inimigos para verificar se o tiro atingiu algum
                    for (int j = 0; j < inimigos.size(); j++) {
                        Inimigo n2 = inimigos.get(j);
                        // Caso atinja, substraio da vida do inimigo o dano do tiro e excluo o tiro do array
                        if((s.getY() <= (n2.getY() + n2.getHeight()) && s.getY() >= n2.getY()) && (s.getX() >= n2.getX() && s.getX() <= (n2.getX() + n2.getWidth()))) {
                            if (n2.receberDano(s.getDano())) {
                                System.out.println("Inimigo destruído");
                                inimigos.remove(j);
                                j--;
                                score++;
                                explosões.add(new Explosion(n2.getX(), n2.getY(), "/images/explosion2.gif", 0.38));
                                explosionInimigo.play();
                                if (n2.getType() == 2) {
                                    mediumDeath = true;
                                    millisMedium = clock.millis();
                                    millisMedium2 = millisMedium;
                                }
                            }
                            shootsPlayer.remove(i);
                            i--;
                        }
                    }
                }

                // Tiros do Inimigo
                for (int i = 0; i < shootsInimigo.size(); i++) {
                    Shoot s = shootsInimigo.get(i);
                    s.draw(g);

                    // Movo eles e, caso passem da tela, excluo do array
                    if (s.move2(n.getX(), n.getY())) {
                        System.out.println("Tiro do inimigo removido");
                        shootsInimigo.remove(i);
                        i--; // Ajusta o índice após a remoção
                        continue;
                    }

                    // Caso atinja o player, subtrai da vida do player o dano do tiro e exclui o tiro do array
                    if(!n.isDead())
                        if (n.receberDano(s.getDano(), s.getX(), s.getY())) {
                            System.out.println("Player atingido, tiro removido");
                            shootsInimigo.remove(i);
                            i--; // Ajusta o índice após a remoção
                            millisDeath = clock.millis();
                            millisDeath2 = millisDeath;
                        }
                }

                // Animação das explosões
                for (int i = 0; i < explosões.size(); i++) {
                    Explosion e = explosões.get(i);

                    if((e.getMillis() - e.getMillis2()) < 500)
                        e.draw(g);
                    else
                        explosões.remove(i);
                }
            }

            // Exibe a tela
            getBufferStrategy().show();

            // Unidade de tempo da animação
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
            }
        }
    }
    public void horda(ArrayList<Inimigo> inimigos, Graphics g) {
        // Nave pequena spawna a cada 1s e em horda a cada 2s
        millisSmall = clock.millis();
        if ((millisSmall - millisSmall2) > timeInimigo && inimigos.size() < 5) {
            inimigos.add(new Inimigo(8, 5, 10, 0.14, width, height, "/images/nave_small.png", 0));
            millisSmall2 = millisSmall;
        }

        if (mediumDeath) {
            millisMedium = clock.millis();
            if ((millisMedium - millisMedium2) > 10000) {
                inimigos.add(new Inimigo(5, 5, 50, 0.35, width, height, "/images/nave_medium.gif", 2));
                millisMedium2 = millisMedium;
                mediumDeath = false;
            }
        }

        // Horda inimiga spawna a cada 15s
        millisHorda = clock.millis();
        if ((millisHorda - millisHorda2) > 15000 || aux) {
            aux = true;
            timeInimigo = 2000;
            millisHorda3 = clock.millis();
            // Cada nave da horda spawna com 700ms de diferença
            if((millisHorda3 - millisHorda4) > 700) {
                inimigos.add(new Inimigo(10, 0.17, width, height, "/images/verme.gif", 1));
                i++;
                millisHorda4 = millisHorda3;
            }

            millisHorda2 = millisHorda;
            if(i > 4) {
                aux = false;
                i = 0;
                timeInimigo = 1000;
            }
        }

        for (int i = 0; i < inimigos.size(); i++) {
            Inimigo n2 = inimigos.get(i);
            n2.draw(g);
            if(n2.getType() == 1) {
                if (n2.horda()) {
                    System.out.println("Inimigo horda removido");
                    inimigos.remove(i);
                    i--;
                }
                continue;
            }
            else if(n2.getType() == 0) {
                if (n2.movimentSmall()) {
                    System.out.println("Inimigo removido");
                    inimigos.remove(i);
                    i--;
                }
                continue;
            }
            n2.movimentMedium();
        }
    }
}