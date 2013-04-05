/**
   Copyright 2012 John Cummens (aka Shadowmage, Shadowmage4513)
   This software is distributed under the terms of the GNU General Public Licence.
   Please see COPYING for precise license information.

   This file is part of Ancient Warfare.

   Ancient Warfare is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Ancient Warfare is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Ancient Warfare.  If not, see <http://www.gnu.org/licenses/>.
 */
package shadowmage.ancient_warfare.common.pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import shadowmage.ancient_warfare.common.config.Config;
import shadowmage.ancient_warfare.common.utils.BlockPosition;

/**
 * going to be a theta-Star implementation
 * (pre-smoothed ASTAR paths), using line-of sight checks
 * at every node-link to recalc optimal g-costs from start-current
 * so that it uses straight-line from A->B directly
 * instead of doing all diagonal moves then all horizontal moves
 * @author Shadowmage
 *
 */
public class PathFinderThetaStar
{
/**
 * A STAR PSUEDOCODE ********************************************************************
 * LISTS : OPEN, CLOSED
 * ADD START NODE TOP OPEN-LIST
 * WHILE OPEN-LIST IS NOT EMPTY
 *    CURRENT = OPEN-LIST.LOWEST-F
 *    IF CURRENT == GOAL THEN END
 *    ADD CURRENT TO CLOSED LIST
 *    LIST NEIGHBORS = CURRENT.GETNEIGHBORS
 *      FOR N IN NEIGHBORS
 *        IF N.CLOSED && C.G + DIST(C,N) > N.G (new path to neighbor is longer than the neighbors current path)
 *          CONTINUE
 *        ELSE IF FRESH NODE OR C.G + DIST(C,N) < N.G (better path found to already-examined node)
 *          N.G = C.G + DIST(C,N)
 *          N.P = C
 *          N.F = N.G + N.H(GOAL)
 *          IF N.CLOSED
 *            REMOVE N FROM CLOSED LIST
 *          IF N NOT IN OPEN SET
 *            ADD TO OPEN SET  
 * **************************************************************************************
 */


/**
 * OPEN-LIST
 */
private PriorityQueue<Node> qNodes = new PriorityQueue<Node>();

/**
 * a list of all working-set nodes, both open and closed.  used to prevent spurious object creation
 * as well as keep already visited but closed nodes scores valid and cached, as well as for pulling
 * live nodes from the 'open-list' without having to manually synch them back in/update values
 */
private ArrayList<Node> allNodes = new ArrayList<Node>();

/**
 * current-node neighbors
 */
private ArrayList<Node> searchNodes = new ArrayList<Node>();

private Node currentNode;

/**
 * start and target points
 */
int sx;
int sy;
int sz;
int tx;
int ty;
int tz;
int maxRange = 80;
PathWorldAccess world;
long startTime;
public long maxRunTime = 15000000l;//15ms
public long maxSearchIterations = 1500;

public List<Node> findPath(PathWorldAccess world, int x, int y, int z, int tx, int ty, int tz, int maxRange)
  {  
  this.world = world;
  this.sx = x;
  this.sy = y;
  this.sz = z;
  this.tx = tx;
  this.ty = ty;
  this.tz = tz;
  this.startTime = System.nanoTime();
  this.maxRange = maxRange;
  this.currentNode = getOrMakeNode(sx, sy, sz, null);
  this.currentNode.g = 0;
  this.currentNode.f = this.currentNode.getH(tx, ty, tz);
  this.qNodes.offer(this.currentNode);
  this.bestEndNode = this.currentNode;
  this.bestPathLength = 0;
  this.bestPathDist = Float.POSITIVE_INFINITY;
  this.searchLoop();
  LinkedList<Node> path = new LinkedList<Node>();
  Node n = this.currentNode;
  Node c = null;
  Node p = null;
  while(n!=null)
    {
    p = c;
    c = new Node(n.x, n.y, n.z);
    c.parentNode = p;
    path.push(c);
//    Config.logDebug(c.toString());
    n = n.parentNode;
    }
  this.currentNode = null;
  this.world = null; 
  this.bestEndNode = null;
  this.allNodes.clear();
  this.qNodes.clear();
  this.searchNodes.clear();
  return path;
  }

private Node bestEndNode = null;
private float bestPathLength = 0.f;
private float bestPathDist = Float.POSITIVE_INFINITY;
private int searchIteration;

private void searchLoop()
  {
  this.searchIteration++;
  while(!qNodes.isEmpty())
    {
    this.currentNode = this.qNodes.poll();
    this.allNodes.add(currentNode);
    if(currentNode.equals(tx, ty, tz))
      {
      break;//goal was hit, found the right path
      }    
    if(shouldTerminateEarly())
      {
//      Config.logDebug("break from path length");
      break;
      }
    currentNode.closed = true;//close the node immediately (equivalent of adding to closed list)  
    this.findNeighbors(currentNode);
    float tent;
    for(Node n : this.searchNodes)
      {  
      tent = currentNode.g + currentNode.getDistanceFrom(n);
      if(n.closed && tent > n.g)//new path from current node to n (already examined node) is longer than n's current path, disregard
        {
        continue;
        }
      if(!qNodes.contains(n) || tent < n.g)//if we haven't seen n before, or if we have but the path through current to n is less than n's best known path
        {//update n's stats to path through current -> n
        //this is where it deviates from A*, we will check to see if n can see the parent of current.  if so
        //we calculate the path to n as if it went through the parent of current, skipping current completely.
        if(canSeeParent(n, currentNode.parentNode))
          {
          n.parentNode = currentNode.parentNode;
          n.g = n.parentNode.g + n.getDistanceFrom(n.parentNode);
          n.f = n.g + n.getH(tx, ty, tz);
          }
        else//else if we cannot skip nodes, link n to current and calc path length
          {
          n.parentNode = currentNode;
          n.g = tent;
          n.f = n.g + n.getH(tx, ty, tz);
          }
        if(!qNodes.contains(n))//if we're not already going to examine n, put it in line to be examined
          {
          qNodes.offer(n);
          }
        n.closed = false;//go ahead and set n to open again...I don't think this really matters....
        }      
      }
    }
  }

private boolean shouldTerminateEarly()  
  {
  long runtime = System.nanoTime() - startTime;
  if(runtime>maxRunTime)
    {
    Config.logDebug("search time exceeded max of: "+(this.maxRunTime/1000000)+"ms, terminating search.");
    return true;
    }
  if(this.searchIteration>this.maxSearchIterations)
    {
    Config.logDebug("search iterations exceeded max of: "+this.maxSearchIterations+ " terminating search.");
    }
  float dist = this.currentNode.getDistanceFrom(tx,ty,tz);
  float len = this.currentNode.getPathLength();
  if( dist < bestPathDist || len > bestPathLength )
    {
    this.bestEndNode = this.currentNode;
    this.bestPathDist = dist;
    this.bestPathLength = len;
    if(len>maxRange)
      {
      Config.logDebug("search length exceeded max of: "+this.maxRange+", terminating search.");      
      return true;
      }
    }
  return false;
  }

private boolean canSeeParent(Node n, Node p)
  {
  if(p==null)
    {
    return false;
    }
  if(n.y!=p.y)
    {
    return false;
    }  
  List<BlockPosition> hits = PathUtils.getPositionsBetween2(n.x, n.z, p.x, p.z);
  for(BlockPosition pos : hits)
    {
    if(!world.isWalkable(pos.x, n.y, pos.z))
      {
      return false;
      }
    }
  return true;
  }

private void findNeighbors(Node n)
  {
  this.searchNodes.clear();  
  tryAddSearchNode(n.x-1, n.y, n.z, n);
  tryAddSearchNode(n.x+1, n.y, n.z, n);
  tryAddSearchNode(n.x, n.y, n.z-1, n);
  tryAddSearchNode(n.x, n.y, n.z+1, n);

  /**
   * diagonals -- check to make sure the path on the crossing blocks is clear
   */
//  tryAddSearchNode( n.x-1, n.y, n.z+1, n);
//  tryAddSearchNode( n.x-1, n.y, n.z-1, n);
//  tryAddSearchNode( n.x+1, n.y, n.z+1, n);
//  tryAddSearchNode( n.x+1, n.y, n.z-1, n);    
  
  if(world.isWalkable(n.x, n.y, n.z+1) && world.isWalkable(n.x-1, n.y, n.z))
    {
    tryAddSearchNode(n.x-1, n.y, n.z+1, n);
    }
  if(world.isWalkable(n.x, n.y, n.z+1)&&world.isWalkable(n.x+1, n.y, n.z))
    {
    tryAddSearchNode(n.x+1, n.y, n.z+1, n);
    }
  if(world.isWalkable(n.x+1, n.y, n.z) && world.isWalkable(n.x, n.y, n.z-1))
    {
    tryAddSearchNode(n.x+1, n.y, n.z-1, n);
    }
  if(world.isWalkable(n.x-1, n.y, n.z) && world.isWalkable(n.x, n.y, n.z-1))
    {
    tryAddSearchNode(n.x-1, n.y, n.z-1, n);
    }

  /**
   * up/down (in case of ladder/water)
   */
  tryAddSearchNode( n.x, n.y+1, n.z, n);
  tryAddSearchNode( n.x, n.y-1, n.z, n);

  /**
   * and the NSEW Y+1/Y-1 jumpable blocks..(not diagonal jumps)
   */
  tryAddSearchNode( n.x-1, n.y+1, n.z, n);
  tryAddSearchNode( n.x+1, n.y+1, n.z, n);
  tryAddSearchNode( n.x, n.y+1, n.z-1, n);
  tryAddSearchNode( n.x, n.y+1, n.z+1, n);

  tryAddSearchNode( n.x-1, n.y-1, n.z, n);
  tryAddSearchNode( n.x+1, n.y-1, n.z, n);
  tryAddSearchNode( n.x, n.y-1, n.z-1, n);
  tryAddSearchNode( n.x, n.y-1, n.z+1, n);  
  }

private void tryAddSearchNode(int x, int y, int z, Node p)
  {
  if(world.isWalkable(x, y, z))
    {
    searchNodes.add(getOrMakeNode(x, y, z, p));
    }
  }

private Node getOrMakeNode(int x, int y, int z, Node p)
  {
  Node n = null;
  for(Node c : this.allNodes)
    {
    if(c.equals(x, y, z))
      {
      return c;
      }
    }
  n = new Node(x,y,z);
  if(p!=null)
    {
    n.parentNode = p;
    n.g = p.g + n.getDistanceFrom(p);
    n.f = n.g + n.getDistanceFrom(tx, ty, tz);
    }  
  allNodes.add(n);
  return n;
  }

}
