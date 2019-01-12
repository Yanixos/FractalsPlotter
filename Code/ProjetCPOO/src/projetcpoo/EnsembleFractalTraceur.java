package projetcpoo;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import java.util.List;

/**
 * Classe qui dessine un ensemble fractal
 */
public class EnsembleFractalTraceur {
    
    private EnsembleFractal ensembleFractal;
    private double zoom;
    private Complexe decalage;
    private PaletteDeCouleurs paletteDeCouleurs;
    private int maxIter;
    private List<Thread> workingThreads = new ArrayList<>();
    
    private static double pasDeZoom = 1.5d, pasDeDecalage = 0.25d;

    /**
    * Constructeur d'un traceur
    * @param ensembleFractal : l'ensemble fractal à dessiner
    * @param zoom : le zoom initiale
    * @param decalage : le décalage initiale par rapport au centre du plan
    * @param paletteDeCouleurs : la palette de couleurs de dessin
    * @param maxIter : le nombre d'itérations maximal par défaut
    */
    public EnsembleFractalTraceur(EnsembleFractal ensembleFractal,
                                double zoom,
                                Complexe decalage,
                                PaletteDeCouleurs paletteDeCouleurs,
                                int maxIter) {
        this.ensembleFractal = ensembleFractal;
        this.zoom = zoom;
        this.decalage = decalage;
        this.paletteDeCouleurs = paletteDeCouleurs;
        this.maxIter = maxIter;
    }
    
    /**
    * Setteur de l'ensemble fractal
    * @param ensembleFractal : le nouveau ensemble fractal
    */
    public void changerEnsembleFractal(EnsembleFractal ensembleFractal){
        this.ensembleFractal = ensembleFractal;
    }
    
    /**
    * Méthode pour zoomer/dézoomer
    * @param agrandir : zoomer ou dézoomer
    */
    public void zoomer(boolean agrandir){
        zoom = agrandir?(zoom / pasDeZoom):(zoom * pasDeZoom);
    }
    
    /**
    * Méthode pour contrôler le décalage
    * @param directionHorizontale : pas de décalage sur l'axe X
    * @param directionVerticale : pas de décalage sur l'axe Y
    */
    public void decaler(int directionHorizontale, int directionVerticale){
        decalage = decalage.plus(new Complexe(directionHorizontale * pasDeDecalage * zoom,
                                            directionVerticale * pasDeDecalage * zoom));
    }
    
    /**
    * Methode qui change de palette de dessin
    * @param palette : l'indice de la nouvelle palette à utiliser
    */
    public void changerPalette(int palette){
        final PaletteDeCouleurs[] p = {PaletteDeCouleurs.PALETTE_1,
                                        PaletteDeCouleurs.PALETTE_2,
                                        PaletteDeCouleurs.PALETTE_3};
        paletteDeCouleurs = p[palette % p.length];
    }
    
    /**
    * Setteur du nombre maximal d'itérations
    * @param maxIter : la nouvelle valeur du nombre maximal d'itérations
    */
    public void changerMaxIter(int maxIter){
        this.maxIter = maxIter;
    }
    
     /**
     * Methode qui calcule la couleur des pixels
     * @param pixelX : la coordonnée X du pixel
     * @param pixelY : la coordonnée Y du pixel
     * @param width  : la largeur de l'espace de dessin
     * @param height : la hauteur de l'espace de dessin
     * @return : la couleur du pixel
     */
    public Color calculerCouleur(int pixelX, int pixelY, int width, int height){
        int ite = (int)(ensembleFractal.indiceDeDivergence(
                        new Complexe(-zoom + 2 * (double)pixelX * zoom/width,
                                -zoom + 2 * (double)pixelY * zoom/height)
                                    .plus(decalage),
                        maxIter) * (256d/maxIter));
        return paletteDeCouleurs.apply(ite);
    }
    
    private void interrompreThreads(){
        for(Thread t : workingThreads){
            t.interrupt();
        }
        workingThreads = new ArrayList<>();
    }
    
    /**
    * Méthode pour déssiner l'ensemble fractal sur une image
    * @param iv : l'image à modifier
    * @param width : la largeur de l'image
    * @param height : la hauteur de l'image
    */
    public void afficherSurEcran(ImageView iv, int width, int height){
        WritableImage wi = new WritableImage(width, height);
        final PixelWriter pw = wi.getPixelWriter();
        interrompreThreads();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i<width;i++){
                    for(int j = 0;j<height;j++){
                        if(Thread.currentThread().isInterrupted()){
                            System.out.println("Working thread interrompu!");
                            return;
                        }
                        final Color c = calculerCouleur(i, j, width, height);
                        pw.setColor(i, j, c);
                    }
                }
                Platform.runLater(()->iv.setImage(wi));
            }
        };
        Thread workingThread;
        (workingThread = new Thread(runnable)).start();
        workingThreads.add(workingThread);
    }
    
    /**
    * Méthode pour déssiner l'ensemble fractal sur une image en effectuant les
    * calculs par plusieurs threads
    * @param iv : l'image à modifier
    * @param width : la largeur de l'image
    * @param height : la hauteur de l'image
    * @param threadCount : nombre de working-threads
    */
    public void afficherSurEcranMultithread
        (ImageView iv, int width, int height, int threadCount){
        interrompreThreads();
        WritableImage wi = new WritableImage(width, height);
        final PixelWriter pw = wi.getPixelWriter();
        for(int t = 0;t < threadCount;t++){
            final int threadNumber = t;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    for(int i = 0;i<width;i++){
                        for(int j = 0;j<height;j++){
                            if((i * width + j)%threadCount != threadNumber){
                                continue;
                            }
                            if(Thread.currentThread().isInterrupted()){
                                System.out.println("Working thread interrompu!");
                                return;
                            }
                            final int x = i;
                            final int y = j;
                            final Color c = calculerCouleur(i, j, width, height);
                            pw.setColor(x, y, c);
                        }
                    }
                }
            };

            Thread workingThread;
            (workingThread = new Thread(runnable)).start();
            workingThreads.add(workingThread);
        }
        
        //attendre la fin de calcul de tous les thread
        //puis affecte le résultat du calcul à l'Image View
        Thread finalThread = new Thread(()->{
            for(Thread t : workingThreads){
                if(t != Thread.currentThread()){
                    try{
                        t.join();
                    }
                    catch(Exception e){
                        System.out.println("Final thread interrompu!");
                    }
                }
            }
            System.out.println("All threads finished processing, joining results...");
            if(!Thread.currentThread().isInterrupted()){
                Platform.runLater(()->iv.setImage(wi));
            }
        });
        workingThreads.add(finalThread);
        finalThread.start();
    }
    
    private static WritableImage cloneImage(WritableImage source){
        final PixelReader pr = source.getPixelReader();
        final WritableImage dest = new WritableImage((int)source.getWidth(),
                (int)source.getHeight());
        final PixelWriter pw = dest.getPixelWriter();
        
        pw.setPixels(0, 0, (int)dest.getWidth(),
                (int)dest.getHeight(), pr, 0, 0);
        
        return dest;
    }
    
    /**
    * Méthode pour déssiner l'ensemble fractal progressivement sur une image
    * @param iv : l'image à modifier
    * @param width : la largeur de l'image
    * @param height : la hauteur de l'image
    */
    public void afficherProgressivementSurEcran
        (ImageView iv, int width, int height){
        interrompreThreads();
        WritableImage wi = new WritableImage(width, height);
        final PixelWriter pw = wi.getPixelWriter();
        
        class ColoriageTemp{
            private boolean diverge = false;
            public Complexe xn;
            public Complexe c;
            
            public ColoriageTemp(int pixelX, int pixelY){
                Complexe coordEcran = 
                        new Complexe(-zoom + 2 * (double)pixelX * zoom/width,
                            -zoom + 2 * (double)pixelY * zoom/height)
                        .plus(decalage);
                xn = ensembleFractal.premierTerme(coordEcran);
                c = ensembleFractal.constanteC(coordEcran);
            }
            
            public boolean verifierDivergence(){
                if(diverge){
                    return true;
                }
                else{
                    xn = ensembleFractal.prochainTerme(xn, c);
                    if(ensembleFractal.diverge(xn)){
                        diverge = true;
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
        }
        
        final ColoriageTemp[][] cache = new ColoriageTemp[width][height];
        for(int i = 0;i < width;i++){
            for(int j = 0;j < height;j++){
                cache[i][j] = new ColoriageTemp(i, j);
            }
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int iter = 0;
                while(true){
                    for(int i = 0;i<width;i++){
                        for(int j = 0;j<height;j++){
                            if(Thread.currentThread().isInterrupted()){
                                System.out.println("Working thread interrompu!");
                                return;
                            }
                            if(!cache[i][j].diverge){
                                cache[i][j].verifierDivergence();
                                final Color c = paletteDeCouleurs.apply(iter%256);
                                pw.setColor(i, j, c);
                            }
                        }
                    }
                    iter++;
                    final WritableImage newImage = cloneImage(wi);
                    Platform.runLater(()->{
                        iv.setImage(newImage);
                    });
                }
            }
        };
        
        Thread workingThread;
        (workingThread = new Thread(runnable)).start();
        workingThreads.add(workingThread);
    }
    
    /**
    * Méthode pour déssiner l'ensemble fractal et exporter le résultat sous
    * forme d'une image png
    * @param fileName : emplacement de l'image png
    * @param width : la largeur de l'image
    * @param height : la hauteur de l'image
    */
    public void exporterPng(String fileName, int width, int height){
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        for(int i = 0;i<width;i++){
            for(int j = 0;j<height;j++){
                final int x = i;
                final int y = j;
                final Color c = calculerCouleur(x, y, width, height);
                
                final java.awt.Color c2 = new java.awt.Color(
                        (int)(c.getRed() * 255),
                        (int)(c.getGreen() * 255),
                        (int)(c.getBlue() * 255));
                g2d.setColor(c2);
                g2d.fillRect(x, y, 1, 1);
            }
        }

        g2d.dispose();
        RenderedImage rendImage = bufferedImage;

        File file = new File(fileName);
        try {
            ImageIO.write(rendImage, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
