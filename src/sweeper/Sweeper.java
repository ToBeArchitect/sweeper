package sweeper;

import java.awt.*;

public class Sweeper {
  Image images;
  public int x;//图片的x坐标
  public int y;//图片的y坐标
  public int i=1;//初始时为图片的序列标记，游戏开始后为图片所表示的数字
  public boolean judge=false;//false说明该图片不是雷，true说明该图片是雷
  public boolean isClicked=false;//false说明该图片未被点击过
  public boolean isMetaDown=false;//false说明图片未被标记
}
