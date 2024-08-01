import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.time.*;
import java.util.Random;

@SuppressWarnings("serial")
public class Run extends javax.swing.JFrame implements Runnable{

    // tamanho da tela
    private int height, width;

    // Variável para controlar o estado de pausa
    private boolean isPaused = false;

    // teclas
    /* Funcionamento: quando a tecla é pressionada sua respectiva variável fica
    com valor True. Quando solta a variável fica com valor False. */
    private boolean left, right, up, down, shoot, horde = false, mediumDeath = true, animationBoss = true, animationCap = true, bigDeath = true, shootPlusSpawn = false, exit = false;
    // Millis (função de tempo) para fazer o delay dos tiros
    Clock clock = Clock.systemDefaultZone();
    long millisSmall = clock.millis(), millisSmall2;
    long millisMedium = clock.millis(), millisMedium2;
    long millisBig = clock.millis(), millisBig2;
    long millisHorda = clock.millis(), millisHorda2;
    long millisHorda3 = clock.millis(), millisHorda4;
    long millisDeath = clock.millis(), millisDeath2;
    long millisDeathBoss = clock.millis(), millisDeathBoss2;
    byte i = 0, randHorde, randUp;
    short inimigoSpawn = 1000, subInimigoSpawn = 0, subTimeTiroInimigo = 0, score = 0, auxScore;

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

        // Objeto da imagem de fundo
        ImageIcon img = new ImageIcon(this.getClass().getResource("/images/background.gif"));
        // Objetos das imagens do score
        ImageIcon imgDec, imgUni, imgScore;

        // Objeto da imagem de explosão do player
        ImageIcon imgExplosion = new ImageIcon(this.getClass().getResource("/images/explosion2.gif"));
        ImageIcon explosionBig = new ImageIcon(this.getClass().getResource("/images/explosionBig.gif"));

        // Objeto do som do loop do jogo
        Sound loopSound = new Sound("/sounds/loop2.wav", true);

        // ArrayList dos tiros do player
        ArrayList<Shoot> shootsPlayer = new ArrayList<>();
        // ArrayList dos tiros dos inimigos
        ArrayList<Shoot> shootsInimigo = new ArrayList<>();
        // ArrayList inimigos
        ArrayList<Nave> inimigos = new ArrayList<>();
        // ArrayList das explosões
        ArrayList<Explosion> explosões = new ArrayList<>();
        // ArrayList dos power ups
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        // ArrayList das capsulas do boss
        ArrayList<Capsula> capsulas = new ArrayList<>();

        // Objeto do player
        Player n = new Player(width/2, height, 12,9, 10, 0.18, width, height, "/images/nave_player.gif");
        // Objeto do boss
        Boss b = new Boss(2, 2, 1000, 0.9, width, height, "/images/boss.png");
        loopSound.play();

        // Animação inicial
        while(n.getY() >= 800) {
            // Atualiza g
            g = getBufferStrategy().getDrawGraphics();

            // Limpa tela
            g.clearRect(0, 0, getWidth(), getHeight());

            // Animação da tela de fundo
            g.drawImage(img.getImage(), 0, 0, width + 50, height, null);

            // Animação da nave player
            n.draw(g);
            n.animation();

            //g.drawImage(boss.getImage(), 0, -700, (int) (boss.getIconWidth() * 1.57), (int) (boss.getIconHeight() * 1.57), null);

            // Exibe a tela
            getBufferStrategy().show();

            // Unidade de tempo da animação
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
            }
        }

        millisHorda = clock.millis();
        millisHorda2 = millisHorda;
        millisMedium = clock.millis();
        millisMedium2 = millisMedium;

        // Batalha inicial
        while(score <= 50) {
            // Atualiza g
            g = getBufferStrategy().getDrawGraphics();

            // Limpa tela
            g.clearRect(0, 0, getWidth(), getHeight());

            // Animação da tela de fundo
            g.drawImage(img.getImage(), 0, 0, width + 50, height, null);

            // Se o jogo estiver pausado, não executa a lógica do jogo
            if (isPaused)
                continue;

            // Pontuação
            int uni = score % 10;
            int dec = score / 10;
            imgDec = new ImageIcon(this.getClass().getResource("/images/score/" + (char) (dec + '0') + ".png"));
            imgUni = new ImageIcon(this.getClass().getResource("/images/score/" + (char) (uni + '0') + ".png"));
            imgScore = new ImageIcon(this.getClass().getResource("/images/score/score.png"));

            // Animação da pontuação
            g.drawImage(imgDec.getImage(), 20, 80, (int) (imgDec.getIconWidth() * 0.2), (int) (imgDec.getIconHeight() * 0.2), null);
            g.drawImage(imgUni.getImage(), 50, 80, (int) (imgUni.getIconWidth() * 0.2), (int) (imgUni.getIconHeight() * 0.2), null);
            g.drawImage(imgScore.getImage(), 32, 60, (int) (imgUni.getIconWidth() * 0.22), (int) (imgUni.getIconHeight() * 0.08), null);

            // Morte do player
            if (n.isDead()) {
                millisDeath = clock.millis();
                if ((millisDeath - millisDeath2) < 400)
                    g.drawImage(imgExplosion.getImage(), n.getX(), n.getY(), (int) (imgExplosion.getIconWidth() * 0.5), (int) (imgExplosion.getIconHeight() * 0.5), null);
                else if ((millisDeath - millisDeath2) > 2500) {
                    loopSound.stop();
                    new TelaGameOver();
                    dispose(); // Fecha a tela de jogo
                    break;
                }
            }

            // Dificuldade
            if(score % 5 == 0 && score != 0) {
                subTimeTiroInimigo = (short) (score * 6);
                subInimigoSpawn = (short) (score * 8);
            }

            // Controles
            if (right)
                n.moveX(1);
            if (left)
                n.moveX(-1);
            if (down)
                n.moveY(1);
            if (up)
                n.moveY(-1);

            // Instanciando
            // Drop de power ups
            if((score - 20) % 12 == 0 && (score - 20) >= 0 && score != auxScore) {
                powerUps.add(new PowerUp(0, 0, width, height, 0.35, false, "images/shieldPower.gif"));
                auxScore = score;
            }

            // Inimigos
            inimigos(inimigos);

            // Tiro do player
            if (n.atirar(0, shoot)) {
                shootsPlayer.add(new Shoot(n.getX(), n.getY(), 10, 10, "/images/tiro.gif", height, width, 0.4, (byte) 0, n.getWidth()));
                shootsPlayer.add(new Shoot(n.getX(), n.getY(), 10, 10, "/images/tiro.gif", height, width, 0.4, (byte) 1, n.getWidth()));
                if(n.getShootPlus()) {
                    shootsPlayer.add(new Shoot(n.getX(), n.getY(), 5, 10, "/images/tiro.gif", height, width, 0.4, (byte) 2, n.getWidth()));
                    shootsPlayer.add(new Shoot(n.getX(), n.getY(), 5, 10, "/images/tiro.gif", height, width, 0.4, (byte) 3, n.getWidth()));
                }
            }

            // Animação
            // Animação da nave player
            n.draw(g);

            // Inimigos
            for (int i = 0; i < inimigos.size(); i++) {
                Nave inimigo = inimigos.get(i);
                inimigo.draw(g);

                // Movimento inimigos
                if (inimigo.move()) {
                    System.out.println("Inimigo de horda removida");
                    inimigos.remove(i);
                    i--;
                    continue;
                }

                // Tiros dos inimigos
                if (inimigo.atirar(subTimeTiroInimigo, true)) {
                    if(inimigos.get(i) instanceof Verme)
                        shootsInimigo.add(new Shoot(inimigo.getX(), inimigo.getY(), 10, 6, "/images/tiroHorda.png", height, width, 0.04));
                    else if(inimigos.get(i) instanceof Small)
                        shootsInimigo.add(new Shoot(inimigo.getX() + (inimigo.getWidth()/2) - 15, inimigo.getY() + inimigo.getHeight() - 20, 10, 7, "/images/tiroInimigo.png", height, width, 0.06));
                    else if(inimigos.get(i) instanceof Medium)
                        shootsInimigo.add(new Shoot(inimigo.getX() + (inimigo.getWidth()/2) - 20, inimigo.getY() + inimigo.getHeight(), 10, 5, "/images/tiroMedium.png", height, width, 0.07));
                    else
                        shootsInimigo.add(new Shoot(inimigo.getX() + (inimigo.getWidth()/2) - 20, inimigo.getY() + 135, 20, 5, "/images/tiroEnergy.png", height, width, 0.25));
                }

                // Colisão com o player
                if(!n.isDead())
                    if ((inimigo.getY() <= (n.getY() + n.getHeight()) && inimigo.getY() >= n.getY()) && inimigo.getX() >= n.getX() && inimigo.getX() <= (n.getX() + n.getWidth())) {
                        if (n.receberDano(10)) {
                            millisDeath = clock.millis();
                            millisDeath2 = millisDeath;
                        } else
                            n.setImg("/images/nave_player.gif", 0.18);
                        if (inimigo.receberDano(10)) {
                            inimigoDelete(inimigo, explosões);
                            inimigos.remove(i);
                            i--;
                        }
                    }
            }

            // Tiros do Player
            for (int i = 0; i < shootsPlayer.size(); i++) {
                // Varro o array de tiros do player para desenhar eles
                Shoot s = shootsPlayer.get(i);
                s.draw(g);
                // Movo eles para cima e, caso passem da tela, excluo do array
                if (s.move()) {
                    shootsPlayer.remove(i);
                    i--;
                    continue;
                }
                // Varro o array de inimigos para verificar se o tiro atingiu algum
                for (int j = 0; j < inimigos.size(); j++) {
                    Nave inimigo = inimigos.get(j);
                    // Caso atinja, substraio da vida do inimigo o dano do tiro e excluo o tiro do array
                    if ((s.getY() <= (inimigo.getY() + inimigo.getHeight()) && s.getY() >= inimigo.getY()) && (s.getX() >= inimigo.getX() && s.getX() <= (inimigo.getX() + inimigo.getWidth()))) {
                        if (inimigo.receberDano(s.getDano())) {
                            inimigos.remove(j);
                            j--;
                            score++;

                            inimigoDelete(inimigo, explosões);

                            Random r = new Random();
                            randUp = (byte) r.nextInt(10);
                            if(score >= 35 && randUp == 1 && !n.getShootPlus() && !shootPlusSpawn) {
                                powerUps.add(new PowerUp(inimigo.getX(), inimigo.getY(), width, height, 0.25, true, "images/tiroPower.gif"));
                                shootPlusSpawn = true;
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
                    shootsInimigo.remove(i);
                    i--; // Ajusta o indice
                    continue;
                }

                // Caso atinja o player, subtrai da vida do player o dano do tiro e exclui o tiro do array
                if(!n.isDead())
                    if ((s.getY() <= (n.getY() + n.getHeight()) && s.getY() >= n.getY()) && (s.getX() >= n.getX() && s.getX() <= (n.getX() + n.getWidth()))) {
                        if (n.receberDano(s.getDano())) {
                            millisDeath = clock.millis();
                            millisDeath2 = millisDeath;
                        }
                        n.setImg("/images/nave_player.gif", 0.18);
                        shootsInimigo.remove(i);
                        i--;
                    }
            }

            // Animação das explosões
            for (int i = 0; i < explosões.size(); i++) {
                Explosion e = explosões.get(i);

                if((e.getMillis() - e.getMillis2()) < 500)
                    e.draw(g);
                else {
                    explosões.remove(i);
                    i--;
                }
            }

            // Animação do power ups
            for (int i = 0; i < powerUps.size(); i++) {
                PowerUp up = powerUps.get(i);
                up.draw(g);

                if (up.move()) {
                    if (!n.isDead()) {
                        if (n.pegarPowerUp(up.getX(), up.getY(), up.getWidth(), up.getHeight(), up.getType())) {
                            powerUps.remove(i);
                            i--;
                        }
                    }
                } else {
                    if(up.getType())
                        shootPlusSpawn = false;

                    powerUps.remove(i);
                    i--;
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

        //Preparativos da boss fight
        long millisAnimation = clock.millis(), millisAnimation2 = millisAnimation;

        // Limpando o arrayList dos inimigos
        for (int i = 0; i < inimigos.size(); i++) {
            Nave inimigo = inimigos.get(i);
            inimigos.remove(i);
            i--;
            inimigoDelete(inimigo, explosões);
        }

        // Limpando o arrayList dos tiros inimigos
        for (int i = 0; i < shootsInimigo.size(); i++) {
            Shoot s = shootsInimigo.get(i);
            shootsInimigo.remove(i);
            i--;
        }

        // Limpando o arrayList dos tiros player
        for (int i = 0; i < shootsPlayer.size(); i++) {
            Shoot s = shootsPlayer.get(i);
            shootsPlayer.remove(i);
            i--;
        }

        // Boss fight
        while(true) {
            // Atualiza g
            g = getBufferStrategy().getDrawGraphics();

            // Limpa tela
            g.clearRect(0, 0, getWidth(), getHeight());

            // Animação da tela de fundo
            g.drawImage(img.getImage(), 0, 0, width + 50, height, null);

            // Se o jogo estiver pausado, não executa a lógica do jogo
            if (isPaused)
                continue;

            // Animação do player
            n.draw(g);

            // Animação do boss
            b.draw(g);

            // Animação das explosões
            for (int i = 0; i < explosões.size(); i++) {
                Explosion e = explosões.get(i);

                if((e.getMillis() - e.getMillis2()) < 500)
                    e.draw(g);
                else {
                    explosões.remove(i);
                    i--;
                }
            }

            // Animação inicial
            if(animationBoss) {
                millisAnimation = clock.millis();
                if ((millisAnimation - millisAnimation2) >= 2000)
                    if (n.animation2()) {
                        if(b.getY() <= 170)
                            b.animation();
                        else {
                            if(animationCap) {
                                for (int i = 0; i < 4; i++)
                                    capsulas.add(new Capsula(b.getX() + (b.getWidth() / 2) - 27, b.getY() + (b.getHeight() / 2) - 50, 2, "images/capsula.png", height, width, 0.5, (byte) i));
                                animationCap = false;
                            }
                            for (int i = 0; i < capsulas.size(); i++) {
                                Capsula c = capsulas.get(i);
                                c.draw(g);

                                c.setmillisShoot();

                                if(c.capsulaAnimation())
                                    animationBoss = false;
                            }
                        }
                    }
            } else if(b.getVida() > 0) {
                // Boss fight

                // Morte do player
                if (n.isDead()) {
                    millisDeath = clock.millis();
                    if ((millisDeath - millisDeath2) < 400)
                        g.drawImage(imgExplosion.getImage(), n.getX(), n.getY(), (int) (imgExplosion.getIconWidth() * 0.5), (int) (imgExplosion.getIconHeight() * 0.5), null);
                    else if ((millisDeath - millisDeath2) > 2500) {
                        loopSound.stop();
                        new TelaGameOver();
                        dispose(); // Fecha a tela de jogo
                        break;
                    }
                }

                // Animação das capsulas
                for (int i = 0; i < capsulas.size(); i++) {
                    Capsula c = capsulas.get(i);
                    c.draw(g);

                    // Movimentação das capsulas
                    c.move(b.getX() + (b.getWidth() / 2) - 27);

                    if(c.atirar())
                        shootsInimigo.add(new Shoot(c.getX(), c.getY(), 10, 6, "/images/tiroCap.png", height, width, 0.5));

                    // Colisão com o player
                    if(!n.isDead())
                        if ((c.getY() <= (n.getY() + n.getHeight()) && c.getY() >= n.getY()) && c.getX() >= n.getX() && c.getX() <= (n.getX() + n.getWidth())) {
                            if (n.receberDano(10)) {
                                millisDeath = clock.millis();
                                millisDeath2 = millisDeath;
                            } else
                                n.setImg("/images/nave_player.gif", 0.18);
                        }
                }

                // Animação do boss
                b.move();
                // Colisão com o player
                if(!n.isDead())
                    if ((b.getY() <= (n.getY() + n.getHeight()) && b.getY() >= n.getY()) && b.getX() >= n.getX() && b.getX() <= (n.getX() + n.getWidth())) {
                        if (n.receberDano(10)) {
                            millisDeath = clock.millis();
                            millisDeath2 = millisDeath;
                        } else
                            n.setImg("/images/nave_player.gif", 0.18);
                    }

                // Tiros do boss
                if(b.atirar(0, true)) {
                    shootsInimigo.add(new Shoot(b.getX() + (b.getWidth() / 2) - 27, b.getY() + (b.getHeight() / 2) - 50, 20, 5, "/images/tiroBoss.gif", height, width, 0.5));
                }

                // Tiros das capsulas
                for (int i = 0; i < shootsInimigo.size(); i++) {
                    Shoot s = shootsInimigo.get(i);
                    s.draw(g);

                    // Movo eles e, caso passem da tela, excluo do array
                    if (s.move2(n.getX(), n.getY())) {
                        shootsInimigo.remove(i);
                        i--; // Ajusta o indice
                    }

                    // Caso atinja o player, subtrai da vida do player o dano do tiro e exclui o tiro do array
                    if(!n.isDead())
                        if ((s.getY() <= (n.getY() + n.getHeight()) && s.getY() >= n.getY()) && (s.getX() >= n.getX() && s.getX() <= (n.getX() + n.getWidth()))) {
                            if (n.receberDano(s.getDano())) {
                                millisDeath = clock.millis();
                                millisDeath2 = millisDeath;
                            }
                            n.setImg("/images/nave_player.gif", 0.18);
                            shootsInimigo.remove(i);
                            i--;
                        }
                }

                // Tiros do Player
                for (int i = 0; i < shootsPlayer.size(); i++) {
                    // Varro o array de tiros do player para desenhar eles
                    Shoot s = shootsPlayer.get(i);
                    s.draw(g);
                    // Movo eles para cima e, caso passem da tela, excluo do array
                    if (s.move()) {
                        shootsPlayer.remove(i);
                        i--;
                        continue;
                    }

                    // Caso atinja, substraio da vida do inimigo o dano do tiro e excluo o tiro do array
                    if ((s.getY() <= (b.getY() + b.getHeight()) && s.getY() >= b.getY()) && (s.getX() >= b.getX() && s.getX() <= (b.getX() + b.getWidth()))) {
                        b.receberDano(s.getDano());
                        shootsPlayer.remove(i);
                        i--;
                    }
                }

                // Controles
                if (right)
                    n.moveX(1);
                if (left)
                    n.moveX(-1);
                if (down)
                    n.moveY(1);
                if (up)
                    n.moveY(-1);

                // Tiro do player
                if (n.atirar(0, shoot)) {
                    shootsPlayer.add(new Shoot(n.getX(), n.getY(), 10, 10, "/images/tiro.gif", height, width, 0.4, (byte) 0, n.getWidth()));
                    shootsPlayer.add(new Shoot(n.getX(), n.getY(), 10, 10, "/images/tiro.gif", height, width, 0.4, (byte) 1, n.getWidth()));
                    if (n.getShootPlus()) {
                        shootsPlayer.add(new Shoot(n.getX(), n.getY(), 5, 10, "/images/tiro.gif", height, width, 0.4, (byte) 2, n.getWidth()));
                        shootsPlayer.add(new Shoot(n.getX(), n.getY(), 5, 10, "/images/tiro.gif", height, width, 0.4, (byte) 3, n.getWidth()));
                    }
                }

                millisAnimation = clock.millis();
                millisAnimation2 = millisAnimation;
                millisDeathBoss = clock.millis();
                millisDeathBoss2 = millisDeathBoss;
            } else {
                // Destruição das capsulas
                for (int i = 0; i < capsulas.size(); i++) {
                    Capsula c = capsulas.get(i);
                    capsulas.remove(i);
                    i--;
                    explosões.add(new Explosion(c.getX(), c.getY(), "/images/explosion2.gif", 0.32));
                }

                millisAnimation = clock.millis();
                if ((millisAnimation - millisAnimation2) >= 2000) {
                    b.setDead(true);
                    // Morte do boss
                    millisDeathBoss = clock.millis();
                    if ((millisDeathBoss - millisDeathBoss2) < 6000)
                        g.drawImage(explosionBig.getImage(), b.getX(), b.getY(), (int) (explosionBig.getIconWidth() * 1.5), (int) (explosionBig.getIconHeight() * 1.5), null);
                    else {
                        if(!exit) {
                            if (n.animation2())
                                exit = true;
                        }
                        else {
                            n.animationFinal();
                            if ((n.getY() + n.getWidth()) < 0) {
                                new TelaWin();
                                dispose();
                                break;
                            }
                        }
                    }
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

    public void inimigos(ArrayList<Nave> inimigos) {
        // Nave pequena spawna a cada 1s e com uma horda na tela a cada 2s
        millisSmall = clock.millis();
        if ((millisSmall - millisSmall2) > (inimigoSpawn - subInimigoSpawn) && inimigos.size() < 5) {
            inimigos.add(new Small(7, 5, 10, 0.14, width, height, "/images/nave_small.png"));
            millisSmall2 = millisSmall;
        }

        // Nave média spawna a cada 10s depois de 15 pontos
        if (mediumDeath) {
            millisMedium = clock.millis();
            if ((millisMedium - millisMedium2) > 10000 && score >= 15) {
                inimigos.add(new Medium(5, 5, 50, 0.35, width, height, "/images/nave_medium.gif"));
                millisMedium2 = millisMedium;
                mediumDeath = false;
            }
        }

        // Nave grande spawna a cada 10s depois de 35 pontos
        if(bigDeath) {
            inimigoSpawn = 1000;
            millisBig = clock.millis();
            if ((millisBig - millisBig2) > 15000 && score >= 35) {
                inimigos.add(new Big(2, 1, 100, 0.95, width, height, "/images/nave_big.gif"));
                millisBig2 = millisBig;
                bigDeath = false;
            }
        } else
            inimigoSpawn = 2000;

        // Horda inimiga spawna a cada 15s
        millisHorda = clock.millis();
        if ((millisHorda - millisHorda2) > 15000 && score >= 25) {
            if(!horde) {
                Random r = new Random();
                randHorde = (byte) r.nextInt(4);
            }
            horde = true;
            inimigoSpawn = 2000;
            millisHorda3 = clock.millis();
            // Cada nave da horda spawna com 700ms de diferença
            if((millisHorda3 - millisHorda4) > 700) {
                inimigos.add(new Verme(10, 0.17, width, height, randHorde, "/images/verme.gif"));
                i++;
                millisHorda4 = millisHorda3;
            }
            if(i > 4) {
                horde = false;
                i = 0;
                inimigoSpawn /= 2;
                millisHorda2 = millisHorda;
            }
        }
    }

    public void inimigoDelete(Nave inimigo, ArrayList<Explosion> explosões) {
        if (inimigo instanceof Medium) {
            explosões.add(new Explosion(inimigo.getX(), inimigo.getY(), "/images/explosionMedium.gif", 0.75));
            mediumDeath = true;
            millisMedium = clock.millis();
            millisMedium2 = millisMedium;
        } else if(inimigo instanceof Big) {
            explosões.add(new Explosion(inimigo.getX() - 20, inimigo.getY(), "/images/explosionBig.gif", 1.0));
            bigDeath = true;
            millisBig = clock.millis();
            millisBig2 = millisMedium;
        } else
            explosões.add(new Explosion(inimigo.getX(), inimigo.getY(), "/images/explosion2.gif", 0.38));
    }
}