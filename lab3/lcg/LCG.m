## Copyright (C) 2015 kepuss
## 
## This program is free software; you can redistribute it and/or modify it
## under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or
## (at your option) any later version.
## 
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
## 
## You should have received a copy of the GNU General Public License
## along with this program.  If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*- 
## @deftypefn {Function File} {@var{retval} =} LCG (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: kepuss <kepuss@Odin>
## Created: 2015-03-24

function [array] = LCG (seed, a ,c, p, n)
  array = zeros(1,n+1);
  array(1) = seed;
  for i = 2:n+1
    array(i) = mod(a*array(i-1) + c,p);
  endfor
  array = array(2:end);
endfunction
