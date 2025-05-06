## Definitions example
```yaml
# Definition of subject types (e.g., user groups, resource groups)
# Each type contains a list of node identifiers it includes
types:
  # A subject group, such as a user group or role
  example:
    nodes:
      - "example.node"
      - "example.node.subnode"

  # A subject group representing resources (e.g., files, endpoints)
  example2:
    nodes:
      - "example2.node"
      - "example2.node2"
      - "example2.node.subnode"

# Default access grants between subject types
# These define default permissions from one subject group (e.g., users)
# to another group (e.g., resources), specifying which nodes are accessible
default-grants:
  - from: example        # Source subject group (e.g., users)
    to: example2         # Target subject group (e.g., resources)
    nodes:
      - "example2.node"  # Resource node that is accessible by default
```