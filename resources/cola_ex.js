var width = 960;
var height = 500;

var color = d3.scale.category10();

nodes = [];
links = [];

var force = cola.d3adaptor()
        .nodes(nodes)
        .links(links)
        .linkDistance(200)
        .size([width, height])
        .on("tick", tick);

var svg = d3.select("body").append("svg")
        .attr("width", width)
        .attr("height", height);

var node = svg.selectAll(".node"),
    link = svg.selectAll(".link");

function start() {

    node = node.data(force.nodes(), function (d) { return d.id; });
    node.enter()
        .data(graph.nodes)
        .enter().append("rect")
        .attr("class", "node")
        .attr("width", function (d) { return d.width; })
        .attr("height", function (d) { return d.height; })
        .attr("rx", 5).attr("ry", 5)
        .style("fill", function (d) { return color(1); })
        .call(cola.drag);

    node.exit().remove();

    link = link.data(force.links(),
                     function (d) { return d.source.id + "-" + d.target.id; });
    link.enter()
        .insert("line", ".node")
        .attr("class", "link")
        .attr("x1", function (d) { return d.source.x; })
        .attr("y1", function (d) { return d.source.y; })
        .attr("x2", function (d) { return d.source.x; })
        .attr("y2", function (d) { return d.source.y; });
    link.exit().remove();

    force.start();
}

function tick() {
    node.transition().attr("cx", function (d) { return d.x; })
        .attr("cy", function (d) { return d.y; })

    link.transition().attr("x1", function (d) { return d.source.x; })
        .attr("y1", function (d) { return d.source.y; })
        .attr("x2", function (d) { return d.target.x; })
        .attr("y2", function (d) { return d.target.y; });
}

function find_node(name){
    var dupe = -1
    for(idx in nodes){ if (nodes[idx].id == name) { dupe = idx; } }
    return dupe;
}

function find_edge(s, t){
    var dupe = -1
    for(idx in links){
        if (links[idx].source.id == s && links[idx].target.id == t) {
            dupe = idx;
        }
    }
    return dupe;
}

function add_node(name){
    if(-1 == find_node(name)){
        nodes.push({id: name});
    } else {
        console.log(name + " already exists.");
    }
}

function add_edge(s, t){
    s_idx = find_node(s);
    t_idx = find_node(t);
    if (-1 != s_idx){
        if (-1 != t_idx){
            if(-1 == find_edge(s, t)){
                links.push({source: nodes[s_idx], target: nodes[t_idx]});
            } else { console.log( "edge [" + s + " -> " + t + "] already exists."); }
        } else { console.log(t + " (target) does not exist."); }
    } else { console.log(s + " (source) does not exist."); }
}

function remove_node(name){
    var idx = find_node(name);
    if(-1 != idx){
        nodes.splice(idx, 1);
    } else { console.log(name + " does not exist.") }
}

function remove_edge(s, t){
    var idx = find_edge(s, t);
    console.log(idx);
    if(-1 != idx){
        links.splice(idx, 1);
    }
}

function clear(){
    while(links.pop()){};
    while(nodes.pop()){};
}
