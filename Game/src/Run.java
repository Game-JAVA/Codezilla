import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.time.*;

@SuppressWarnings("serial")
public class Run extends javax.swing.JFrame implements Runnable{

    // tamanho da tela
    private int height;
    private int width;

    // teclas
    /*
     * Funcionamento: quando a tecla é pressionada sua respectiva variável fica
     * com valor True. Quando solta a variável fica com valor False.
     */
    private boolean left, right, up, down, shoot;

    // Construtor
    public Run() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.width;
        height = screenSize.height;

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
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_A) {
            left = true;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_D) {
            right = true;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_W)
        {
            up = true;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_S) {
            down = true;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            shoot = true;
        }
    }//GEN-LAST:event_formKeyPressed

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
        ImageIcon img = new ImageIcon(this.getClass().getResource("/images/fundo.png"));

        // ArrayList dos inimigos e tiros, pois irá ter vários
        ArrayList<Shoot> shootsPlayer = new ArrayList<>();
        ArrayList<Shoot> shootsInimigo = new ArrayList<>();
        ArrayList<Inimigo> inimigos = new ArrayList<>();

        Inimigo n = new Inimigo(10, 10, 5,5, 10, 0.2, 20, "/images/nave_player.gif");
        inimigos.add(new Inimigo(500, 500, 1,1, 10, 0.15, 200,"/images/nave_small.png"));

        // Millis (função de tempo) para fazer o delay dos tiros
        Clock clock = Clock.systemDefaultZone();
        long milis = clock.millis();
        long milis2 = milis;

        while(true) {
            // Atualiza g
            g = getBufferStrategy().getDrawGraphics();

            // Limpa tela
            g.clearRect(0, 0, getWidth(), getHeight());

            // Animação da tela de fundo
            g.drawImage(img.getImage(), 0, 0, width, height, null);

            // Animação das naves
            n.draw(g);
            for (int i = 0; i < inimigos.size(); i++) {
                Inimigo n2 = inimigos.get(i);
                n2.draw(g);
                n2.moviment();
            }

            // Controles
            if(right)
                n.moveX(1);
            if(left)
                n.moveX(-1);
            if(down)
                n.moveY(1);
            if(up)
                n.moveY(-1);

            // Tiro do player a cada 500ms
            milis = clock.millis();

            if((milis - milis2) > 500 && shoot) {
                shootsPlayer.add(new Shoot(n.getX(), n.getY(), 10, "/images/tiro.gif", 0.4));
                milis2 = milis;
            }

            // Tiros dos inimigos a cada 1s
            milis = clock.millis();

            for (int i = 0; i < inimigos.size(); i++) {
                Inimigo n2 = inimigos.get(i);
                if((n2.getMillis() - n2.getMillis2()) > 1000) {
                    shootsInimigo.add(new Shoot(n2.getX(), n2.getY(), 10, "/images/tiro.gif", 0.4));
                    n2.setMillis();
                }
            }

            // Animação dos tiros
            for (int i = 0; i < shootsPlayer.size(); i++) {
                // Varro o array de tiros do player para desenhar eles
                Shoot s = shootsPlayer.get(i);
                s.draw(g);
                // Movo eles para cima e, caso passem da tela, excluo do array
                if (s.move(height)) {
                    shootsPlayer.remove(i);
                    i--;
                }
                // Varro o array de inimigos para verificar se o tiro atingiu algum
                for (int j = 0; j < inimigos.size(); j++) {
                    Inimigo n2 = inimigos.get(j);
                    // Caso atinja, substraio da vida do inimigo o dano do tiro e excluo o tiro do array
                    if(n2.receberDano(s.getDano(), s.getX(), s.getY())) {
                        inimigos.remove(j);
                        shootsPlayer.remove(i);
                        i--;
                        j--;
                    }
                }
            }

            for (int i = 0; i < shootsInimigo.size(); i++) {
                Shoot s = shootsInimigo.get(i);
                s.draw(g);
                s.move2(n.getX(), n.getY());
            }

            // Exibe a tela
            getBufferStrategy().show();

            // Unidade de tempo da animação
            try {
                Thread.sleep(5);
            }catch  (InterruptedException ex) {}
        }
    }
}