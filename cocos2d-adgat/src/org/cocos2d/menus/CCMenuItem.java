package org.cocos2d.menus;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/** CCMenuItem base class
 *
 *  Subclass CCMenuItem (or any subclass) to create your custom CCMenuItem objects.
 */

public class CCMenuItem extends CCNode {
    public static final int kItemSize = 24;

    static int _fontSize = kItemSize;
    static String fontName = "DroidSans";

    public static final int kCurrentItem = 0xc0c05001;
    public static final int kZoomActionTag = 0xc0c05002;

    protected boolean isEnabled_;
    /** returns whether or not the item is selected @since v0.8.2 */
    protected boolean isSelected_;

    protected Object targetCallback;
    protected String selector;

    private Method invocation;


    /** Creates a CCMenuItem with a target/selector */
    public CCMenuItem item(Object target, String selector) {
        return new CCMenuItem(target, selector);
    }

    /**
     * Initializes a menu item with a target/selector
     */
    protected CCMenuItem(Object rec, String cb) {
        targetCallback = rec;
        selector = cb;

        setAnchorPoint(CGPoint.make(0.5f, 0.5f));

        invocation = null;
        if (rec != null && cb != null) {
        	Class<?> cls = rec.getClass();
        	try {
        		//invocation = cls.getMethod(cb, Object.class);
        		// +AS:04092013. have to change the usage of getMethod to pass only 1 arg
        		invocation = cls.getMethod(cb);
        	} catch (SecurityException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	} catch (NoSuchMethodException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        }

        isEnabled_ = true;
        isSelected_ = false;
    }

    /**
     * Activate the item
     */
    public void activate() {
        if (isEnabled_) {
        	if (targetCallback != null & invocation != null) {
        		try {
        			//invocation.invoke(targetCallback, this);
        			// +AS:04092013. have to change the way of a method is invoked.
        			invocation.invoke(targetCallback);
        		} catch (IllegalArgumentException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		} catch (IllegalAccessException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		} catch (InvocationTargetException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	}
        }
    }
    
    // +AS:04092013. added set target with callback
    public void setTarget(Object p_target, String p_callback)
    {
    	targetCallback = p_target;
        selector = p_callback;
        
        invocation = null;
        if( p_target != null && p_callback != null ) 
        {
        	Class<?> cls = p_target.getClass();
        	try 
        	{
        		invocation = cls.getMethod(p_callback);
        	} catch (SecurityException e) 
        	{
        		e.printStackTrace();
        	} catch (NoSuchMethodException e) 
        	{
        		e.printStackTrace();
        	}
        }
    }

    /**
     * The item was selected (not activated), similar to "mouse-over"
     */
    public void selected() {
        isSelected_ = true;
    }

    /**
     * The item was unselected
     */
    public void unselected() {
        isSelected_ = false;
    }

    /**
     * Enable or disabled the MenuItem
     */
    public void setIsEnabled(boolean enabled) {
        isEnabled_ = enabled;
    }

    /**
     * Returns whether or not the MenuItem is enabled
     */
    public boolean isEnabled() {
        return isEnabled_;
    }

    /**
     * Returns the outside box
     */
    public CGRect rect() {
    	CGPoint pos = getPositionRef();
    	CGPoint pnt = getAnchorPointRef();
    	CGSize size = getContentSizeRef();
        return CGRect.make(pos.x - size.width * pnt.x, pos.y -
                size.height * pnt.y,
                size.width, size.height);
    }

    /**
     * Returns the outside box to
     * No garbage version.
     */
    public void rect(CGRect ret) {
    	CGPoint pos = getPositionRef();
    	CGPoint pnt = getAnchorPointRef();
    	CGSize size = getContentSizeRef();
        ret.set(pos.x - size.width * pnt.x, pos.y -
                size.height * pnt.y,
                size.width, size.height);
    }
}


