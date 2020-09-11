package water.util;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import water.DKV;
import water.TestUtil;
import water.fvec.Frame;
import water.fvec.TestFrameBuilder;
import water.fvec.Vec;

import java.util.Arrays;

/**
 * Test VecUtils interface.
 */
public class VecUtilsTest extends TestUtil {
  @BeforeClass
  static public void setup() {  stall_till_cloudsize(1); }

  @Test
  public void testStringVec2Categorical() {
    Frame f = parse_test_file("smalldata/junit/iris.csv");
    try {
      Assert.assertTrue(f.vec(4).isCategorical());
      int categoricalCnt = f.vec(4).cardinality();
      f.replace(4, f.vec(4).toStringVec()).remove();
      DKV.put(f);
      Assert.assertTrue(f.vec(4).isString());
      f.replace(4, f.vec(4).toCategoricalVec()).remove();
      DKV.put(f);
      Assert.assertTrue(f.vec(4).isCategorical());
      Assert.assertEquals(categoricalCnt, f.vec(4).cardinality());
    } finally {
      f.delete();
    }
  }

  @Test
  public void collectIntegerDomain() {

    String[] data = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "11"};

    Frame frame = null;
    try {
      frame = new TestFrameBuilder().withColNames("C1")
              .withName("testFrame")
              .withVecTypes(Vec.T_CAT)
              .withDataForCol(0, data)
              .build();
      Assert.assertNotNull(frame);

      final long[] levels = new VecUtils.CollectIntegerDomain().doAll(frame.vec(0)).domain();
      Assert.assertEquals(levels.length, data.length);
    } finally {
      if (frame != null) frame.remove();
    }
  }

  @Test
  public void testMakeGaussian() {
    Vec v = null;
    try {
      v = VecUtils.makeGaussianVec(5, 3,0xCECE);
      Assert.assertArrayEquals(new int[]{0, 1, 0, 0, 2}, FrameUtils.asInts(v)); 
    } finally {
      if (v != null) 
        v.remove(); 
    }
  }

  @Test
  public void testMakeGaussian2() {
    Vec v = null;
    try {    
      v = VecUtils.makeGaussianVec(5,0xCECE);
      Assert.assertArrayEquals(new int[]{-1, 1, 0, 0, 2}, FrameUtils.asInts(v));
    } finally {
      if (v != null) 
        v.remove();
    }
  }  

  @Test
  public void testUniformDistrFromFrame() {
    Frame frame = null;
    Vec dist = null;
    try {
      frame = parse_test_file("smalldata/anomaly/single_blob.csv");
      dist = VecUtils.uniformDistrFromFrame(frame, 0xDECAF);
      Assert.assertNotNull(dist);
      Assert.assertEquals(-2.69738, dist.min(), 10e-5);
      Assert.assertEquals(2.56836, dist.max(), 10e-5);
    } finally {
      if (frame != null) 
        frame.remove();
      if (dist != null)
        dist.remove();
    }
  }

  @Test
  public void testUniformDistrFromFrame2() {
    Frame frame = null;
    Vec dist = null;
    try {
      frame = parse_test_file("smalldata/anomaly/single_blob_2.csv");
      dist = VecUtils.uniformDistrFromFrame(frame, 0xDECAF);
      Assert.assertNotNull(dist);
      Assert.assertEquals( -2.98850, dist.min(), 10e-5);
      Assert.assertEquals( 3.8769, dist.max(), 10e-5);
    } finally {
      if (frame != null)
        frame.remove();
      if (dist != null)
        dist.remove();
    }    
  }  
  
  @Test
  public void testUniformDistrFromFrameMR() {
    Frame frame = null;
    Vec dist = null;
    try {
      frame = parse_test_file("smalldata/anomaly/single_blob.csv");
      dist = VecUtils.uniformDistrFromFrameMR(frame, 0xDECAF);
      Assert.assertNotNull(dist);
      Assert.assertEquals(-2.69738, dist.min(), 10e-5);
      Assert.assertEquals(2.56836, dist.max(), 10e-5);
    } finally {
      if (frame != null)
        frame.remove();
      if (dist != null)
        dist.remove();
    }
  }

  @Test
  public void testUniformDistrFromFrameMR2() {
    Frame frame = null;
    Vec dist = null;
    try {
      frame = parse_test_file("smalldata/anomaly/single_blob_2.csv");
      dist = VecUtils.uniformDistrFromFrameMR(frame, 0xDECAF);
      Assert.assertNotNull(dist);
      Assert.assertEquals( -2.98850, dist.min(), 10e-5);
      Assert.assertEquals( 3.8769, dist.max(), 10e-5);
    } finally {
      if (frame != null)
        frame.remove();
      if (dist != null)
        dist.remove();
    }
}  
}
